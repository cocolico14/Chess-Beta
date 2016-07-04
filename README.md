1- include PVP(Player vs Player) and PVE(Player vs Env(computer)) mode 1.1- Players name for PVP 1.2- Player name and chosing alinece for PVE

2- include newgame and savegame 1.1- using files to save the game 1.2- keeping : Cord of Glazes x32, Died one have -1 Cord, Players Names, Player's Turn, Move Counter

3- include En Passant

4- include Castling

5- include Promoting Pawn

6- include CheckMate 6.1- checking unSafe bases for Kings with additional Thread with Valid kill of other Glazes 6.2- calc Valid moves and Valid kill with another additional Thread 6.3- removing unSafe bases from Valid moves of Kings 6.4- if valid move were empty then check that if other Glazes can rescue their King if not then that Player is Check Mate

7- include AI for PVE 7.1- keeping Turn for computer 7.2- if it was Computer's Turn then check Computer's Glazes 7.3- any of Glazes that can Kill are in Piority and Possiblity in order of Importance 7.4- any of Glazes that can Move are in Possibility 7.4- if after move of those Glazes their King were in a safe place 7.5- if that move was in Priority than make it happen 7.6- if any of Prioritys were not Valid then choose a random Possibility 7.7- all of these are in another additional Thread
