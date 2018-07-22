1- include PVP(Player vs Player) and PVE(Player vs Env(computer)) mode <br />
  1.1- Players name for PVP<br />
  1.2- Player name and chosing alinece for PVE <br />
  ![game menu](./Screen%20Shots/1-%20Game%20Menu.jpg)
  
  ![PVP mode](./Screen%20Shots/3-%20Changing%20Player's%20Name%20in%20PVP%20mode.jpg)
  
  ![PVE mode](./Screen%20Shots/8-%20Changing%20Player's%20Name%20in%20PVE%20mode.jpg)

2- include newgame and savegame <br />
  1.1- using files to save the game <br />
  1.2- keeping : Cord of Glazes x32, Died one have -1 Cord, Players Names, Player's Turn, Move Counter <br />
  ![newgame loadgame](./Screen%20Shots/2-%20Newgame%20or%20Loadgame.jpg)

3- include En Passant <br />
  ![en passant](./Screen%20Shots/5-%20En%20Passant.jpg)

4- include Castling <br />
  ![castling](./Screen%20Shots/6-%20Castling.jpg)

5- include Promoting Pawn <br />
  ![promote pawn](./Screen%20Shots/7-%20Promoting%20Pawn.jpg)

6- include CheckMate <br />
  6.1- checking unSafe bases for Kings with additional Thread with Valid kill of other Glazes <br />
  6.2- calc Valid moves and Valid kill with another additional Thread <br />
  6.3- removing unSafe bases from Valid moves of Kings <br />
  6.4- if valid move were empty then check that if other Glazes can rescue their King if not then that Player is Check Mate <br />
  ![checked](./Screen%20Shots/4-%20Checked%20by%20Pawn.jpg)
  ![mate](./Screen%20Shots/10%20-%20Check%20Mate.jpg)

7- include AI for PVE <br />
  7.1- keeping Turn for computer <br />
  7.2- if it was Computer's Turn then check Computer's Glazes <br />
  7.3- any of Glazes that can Kill are in Piority and Possiblity in order of Importance <br /> 
  7.4- any of Glazes that can Move are in Possibility <br />
  7.4- if after move of those Glazes their King were in a safe place <br />
  7.5- if that move was in Priority than make it happen <br />
  7.6- if any of Prioritys were not Valid then choose a random Possibility <br />
  7.7- all of these are in another additional Thread <br />
  ![AI](./Screen%20Shots/9-%20AI%20nad%20PVE.jpg)

8- new GUI <br />
  ![gui](./Screen%20Shots/11-new%20GUI.jpg)
