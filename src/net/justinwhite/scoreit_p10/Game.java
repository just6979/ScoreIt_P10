/*
 Copyright (c) 2015 Justin White <jw@justinwhite.net>
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 3. Neither the name of the copyright holder nor the names of its
 contributors may be used to endorse or promote products derived from this
 software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 */

package net.justinwhite.scoreit_p10;

import java.util.*;

class Game {
    static final int MAX_PHASE;
    private static final int MIN_PLAYERS;
    private static final int MAX_PLAYERS;

    static {
        MAX_PHASE = 10;
        MIN_PLAYERS = 2;
        MAX_PLAYERS = 8;
    }

    private final UUID id;
    private final List<Player> players;
    private final Map<String, Player> playerMap;
    private int numPlayers;
    private String name;
    private Player winner;

    public Game(int _numPlayers) {
        this.id = UUID.randomUUID();

        // sanity check number of players
        if (_numPlayers < MIN_PLAYERS) {
            _numPlayers = MIN_PLAYERS;
        } else if (_numPlayers > MAX_PLAYERS) {
            _numPlayers = MAX_PLAYERS;
        }

        numPlayers = 0;
        name = "";
        players = new ArrayList<Player>(_numPlayers);
        playerMap = new TreeMap<String, Player>();

        // add default players
        for (int i = 1; i <= _numPlayers; i++) {
            addPlayer(String.format("Player %d", i));
        }
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\nPlayerMap: %s",
                             getName(),
                             getID(),
                             getNumPlayers(),
                             // List<> and Map<> classes handle toString() themselves
                             players,
                             playerMap
        );
    }

    public UUID getID() {
        return id;
    }

    public void buildName() {
        name = "";
        for (Player p : players) {
            name += p.getInitials();
        }
    }

    public String getName() {
        return name;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<Player> getPlayerList() {
        return players;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public Player getPlayerByName(String _name) {
        return playerMap.get(_name);
    }

    public Boolean checkPlayer(String _name) {
        return playerMap.containsKey(_name);
    }

    public void addPlayer(String _name) {
        Player newPlayer = new Player(_name);
        players.add(numPlayers++, newPlayer);
        playerMap.put(_name, newPlayer);
        buildName();
    }

    public void renamePlayer(String oldName, String newName) {
        Player p = playerMap.remove(oldName);
        if (p != null) {
            p.setName(newName);
            playerMap.put(newName, p);
        }
        buildName();
    }

    public String getScores() {
        String out = "";
        for (Player p : players) {
            out += String.format("%s: %4d Points", p.getName(), p.getScore());
            if (p.getPhase() > 0) {
                out += String.format(", Phase #%d completed\n", p.getPhase());
            } else {
                out += "\n";
            }
        }
        return out;
    }

    // TODO: handle multiple winners: tie-break on score
    public Boolean hasWinner() {
        for (Player p : players) {
            if (Player.winner == p) {
                winner = p;
                return true;
            }
        }
        return false;

    }

    public Player getWinner() {
        return winner;
    }
}
