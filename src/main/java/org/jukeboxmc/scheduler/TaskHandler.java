/*
 * Copyright 2021 WaterdogTEAM
 * Licensed under the GNU General Public License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jukeboxmc.scheduler;

import org.jukeboxmc.logger.Logger;

/**
 * @author WaterdogPE
 * @version 1.0
 */
public class TaskHandler {

    private final int taskId;
    private final boolean async;

    private final Runnable task;

    private int delay;
    private int period;

    private long lastRunTick;
    private long nextRunTick;

    private boolean cancelled;

    public TaskHandler( Runnable task, int taskId, boolean async ) {
        this.task = task;
        this.taskId = taskId;
        this.async = async;
    }

    public void onRun( long currentTick ) {
        this.lastRunTick = currentTick;
        try {
            this.task.run();
        } catch ( Exception e ) {
            Logger.getInstance().error( "Exception while running task!" + e );
        }
    }

    public void cancel() {
        if ( this.cancelled ) {
            return;
        }

        if ( this.task instanceof Task ) {
            ( (Task) task ).onCancel();
        }
        this.cancelled = true;
    }

    public boolean calculateNextTick( long currentTick ) {
        if ( this.isCancelled() || !this.isRepeating() ) {
            return false;
        }
        this.nextRunTick = currentTick + this.period;
        return true;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public boolean isAsync() {
        return this.async;
    }

    public Runnable getTask() {
        return this.task;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay( int delay ) {
        this.delay = delay;
    }

    public boolean isDelayed() {
        return this.delay > 0;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod( int period ) {
        this.period = period;
    }

    public boolean isRepeating() {
        return this.period > 0;
    }

    public long getLastRunTick() {
        return this.lastRunTick;
    }

    public long getNextRunTick() {
        return this.nextRunTick;
    }

    public void setNextRunTick( long nextRunTick ) {
        this.nextRunTick = nextRunTick;
    }
}
