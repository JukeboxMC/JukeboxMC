package org.jukeboxmc.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.jukeboxmc.Server;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WaterdogPE
 * @version 1.0
 */
public class Scheduler {

    private static Scheduler instance;
    private final Server server;

    private final ExecutorService threadedExecutor;

    private final Map<Integer, TaskHandler> taskHandlerMap = new ConcurrentHashMap<>();
    private final Map<Integer, LinkedList<TaskHandler>> assignedTasks = new ConcurrentHashMap<>();
    private final LinkedList<TaskHandler> pendingTasks = new LinkedList<>();

    private final AtomicInteger currentId = new AtomicInteger();

    public Scheduler( Server server ) {
        if ( instance != null ) {
            throw new RuntimeException( "Scheduler was already initialized!" );
        }
        instance = this;
        this.server = server;

        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        builder.setNameFormat( "JukeboxMC Scheduler Executor" );
        this.threadedExecutor = new ThreadPoolExecutor( Runtime.getRuntime().availableProcessors(), Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), builder.build() );
    }

    public static Scheduler getInstance() {
        return instance;
    }

    public TaskHandler executeAsync( Runnable task ) {
        return this.execute( task, true );
    }

    public TaskHandler execute( Runnable task, boolean async ) {
        return this.addTask( task, 0, 0, async );
    }

    public TaskHandler scheduleDelayed( Runnable task, int delay ) {
        return this.scheduleDelayed( task, delay, false );
    }

    public TaskHandler scheduleDelayed( Runnable task, int delay, boolean async ) {
        return this.addTask( task, delay, 0, async );
    }

    public TaskHandler scheduleRepeating( Runnable task, int period ) {
        return this.scheduleRepeating( task, period, false );
    }

    public TaskHandler scheduleRepeating( Runnable task, int period, boolean async ) {
        return this.addTask( task, 0, period, async );
    }

    public TaskHandler scheduleDelayedRepeating( Runnable task, int delay, int period ) {
        return this.scheduleDelayedRepeating( task, delay, period, false );
    }

    public TaskHandler scheduleDelayedRepeating( Runnable task, int delay, int period, boolean async ) {
        return this.addTask( task, delay, period, async );
    }

    public TaskHandler addTask( Runnable runnable, int delay, int period, boolean async ) {
        if ( delay < 0 || period < 0 ) {
            throw new RuntimeException( "Attempted to register a task with negative delay or period!" );
        }

        int currentTick = this.getCurrentTick();
        int taskId = this.currentId.getAndIncrement();

        TaskHandler handler = new TaskHandler( runnable, taskId, async );
        handler.setDelay( delay );
        handler.setPeriod( period );
        handler.setNextRunTick( handler.isDelayed() ? currentTick + delay : currentTick );

        if ( runnable instanceof Task ) {
            Task task = (Task) runnable;
            task.setHandler( handler );
        }

        this.pendingTasks.add( handler );
        this.taskHandlerMap.put( taskId, handler );
        return handler;
    }

    public void onTick( int currentTick ) {
        TaskHandler task;
        while ( ( task = this.pendingTasks.poll() ) != null ) {
            int tick = Math.max( currentTick, task.getNextRunTick() );
            this.assignedTasks.computeIfAbsent( tick, integer -> new LinkedList<>() ).add( task );
        }

        LinkedList<TaskHandler> queued = this.assignedTasks.remove( currentTick );
        if ( queued == null ) return;

        for ( TaskHandler taskHandler : queued ) {
            this.runTask( taskHandler, currentTick );
        }
    }

    private void runTask( TaskHandler taskHandler, int currentTick ) {
        if ( taskHandler.isCancelled() ) {
            this.taskHandlerMap.remove( taskHandler.getTaskId() );
            return;
        }

        if ( taskHandler.isAsync() ) {
            this.threadedExecutor.execute( () -> taskHandler.onRun( currentTick ) );
        } else {
            taskHandler.onRun( currentTick );
        }

        if ( taskHandler.calculateNextTick( currentTick ) ) {
            this.pendingTasks.add( taskHandler );
            return;
        }

        TaskHandler removed = this.taskHandlerMap.remove( taskHandler.getTaskId() );
        removed.cancel();
    }

    public void shutdown() {
        this.server.getLogger().debug( "Scheduler shutdown initialized!" );
        this.threadedExecutor.shutdown();

        int count = 25;
        while ( !this.threadedExecutor.isTerminated() && count-- > 0 ) {
            try {
                this.threadedExecutor.awaitTermination( 100, TimeUnit.MILLISECONDS );
            } catch ( InterruptedException ignore ) {}
        }
    }


    public int getCurrentTick() {
        return this.server.getCurrentTick();
    }

}
