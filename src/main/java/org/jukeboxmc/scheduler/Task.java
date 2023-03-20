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

import org.jetbrains.annotations.Nullable;

/**
 * @author WaterdogPE
 * @version 1.0
 */
public abstract class Task implements Runnable {

    private @Nullable TaskHandler handler = null;

    public abstract void onRun( long currentTick );

    public abstract void onCancel();

    @Override
    public void run() {
        this.onRun( this.handler == null ? -1 : this.handler.getLastRunTick() );
    }

    public int getTaskId() {
        return this.handler == null ? -1 : this.handler.getTaskId();
    }

    public void cancel() {
        if (this.handler != null) {
            this.handler.cancel();
        }
    }

    public @Nullable TaskHandler getHandler() {
        return this.handler;
    }

    public void setHandler( TaskHandler handler ) {
        if ( this.handler != null ) {
            throw new SecurityException( "Can not change task handler!" );
        }
        this.handler = handler;
    }
}
