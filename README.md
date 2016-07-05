1- include PVP(Player vs Player) and PVE(Player vs Env(computer)) mode <br />
  1.1- Players name for PVP<br />
  1.2- Player name and chosing alinece for PVE <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/1- Game Menu.jpg)
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/3- Changing Player's Name in PVP mode.jpg)
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/8- Changing Player's Name in PVE mode.jpg)

2- include newgame and savegame <br />
  1.1- using files to save the game <br />
  1.2- keeping : Cord of Glazes x32, Died one have -1 Cord, Players Names, Player's Turn, Move Counter <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/2- Newgame or Loadgame.jpg)

3- include En Passant <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/5- En Passant.jpg)

4- include Castling <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/6- Castling.jpg)

5- include Promoting Pawn <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/7- Promoting Pawn.jpg)

6- include CheckMate <br />
  6.1- checking unSafe bases for Kings with additional Thread with Valid kill of other Glazes <br />
  6.2- calc Valid moves and Valid kill with another additional Thread <br />
  6.3- removing unSafe bases from Valid moves of Kings <br />
  6.4- if valid move were empty then check that if other Glazes can rescue their King if not then that Player is Check Mate <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/4- Checked by Pawn.jpg)
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/10 - Check Mate.jpg)

7- include AI for PVE <br />
  7.1- keeping Turn for computer <br />
  7.2- if it was Computer's Turn then check Computer's Glazes <br />
  7.3- any of Glazes that can Kill are in Piority and Possiblity in order of Importance <br /> 
  7.4- any of Glazes that can Move are in Possibility <br />
  7.4- if after move of those Glazes their King were in a safe place <br />
  7.5- if that move was in Priority than make it happen <br />
  7.6- if any of Prioritys were not Valid then choose a random Possibility <br />
  7.7- all of these are in another additional Thread <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/9- AI nad PVE.jpg)

8- new GUI <br />
  ![ScreenShot](https://raw.github.com/cocolico14/Chess-Beta/master/Screen Shots/11-new GUI.jpg)
