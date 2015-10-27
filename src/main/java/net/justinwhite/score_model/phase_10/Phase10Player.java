/*
 * Copyright (c) 2015 Justin White <jw@justinwhite.net>
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  3. Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from this
 *  software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package net.justinwhite.score_model.phase_10;

import net.justinwhite.score_model.Player;
import net.justinwhite.score_model.phase_10.Phase10Game.*;

public class Phase10Player extends Player {
    public enum PhaseState {
        INACTIVE, ACTIVE, COMPLETED
    }

    private PhaseState[] phases;
    private int curPhase;

    public Phase10Player() {
        this("Player X");
    }

    public Phase10Player(String _name) {
        this(_name, PhaseSet.ALL);
    }

   public Phase10Player(String _name, PhaseSet phasePreset) {
        this(_name, Phase10Game.getPhasePreset(phasePreset));
    }

    public Phase10Player(String _name, boolean[] _phases) {
        super(_name);
        phases = new PhaseState[Phase10Game.MAX_PHASE + 1];
        setPhases(_phases);
        curPhase = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "%s; Phase %d",
                super.toString(),
                getCurPhase()
        );
    }

    public int getCurPhase() {
        return curPhase;
    }

    public void completePhase() {
        do {
            curPhase++;
            if (curPhase > Phase10Game.MAX_PHASE) {
                curPhase = Phase10Game.MAX_PHASE;
            }
        } while (phases[curPhase] != PhaseState.ACTIVE);
    }

    public void addScore(int _score) {
        setScore(getScore() + _score);
    }

    public void setPhases(boolean[] _phases) {
        for (int i = 0; i <= Phase10Game.MAX_PHASE; i++) {
            if (_phases[i]) {
                phases[i] = PhaseState.ACTIVE;
            } else {
                phases[i] = PhaseState.INACTIVE;
            }
        }
    }
}
