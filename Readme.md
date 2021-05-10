**Projet INFO-H2001 : jeu d'échecs**

**auteurs :** 
Valentine Ackermans
Kubilay Alpaslan
Oualid Ramdani

**Utilisation :**
Code -> Download zip  [(ou cliquer ici)](https://github.com/KetchupOnWaffles/chess/archive/refs/heads/main.zip)
Extraire le zip
Ouvrir dans Android Studio et run sur un téléphone ou émulateur



**Rapport :**

**1 Introduction**

Dans le cadre du cours d’informatique, une application Android implémentant les conceptsde base de la programmation orientée objet a dû être conçue. Ce rapport retrace la concep-tion d’un jeu d’échec en Kotlin. Il partira de l’architecture du projet et de ses diagrammes defonctionnement pour finir avec les fonctionnalités implémentées et les images du jeu.

**2 Architecture du projet**
Le projet est subdivisé en 10 classes. La classe MainActivity constitue la partie centrale duprojet puisqu’elle est responsable à elle seule du démarrage de l’application et de sa synchroni-sation avec les informations graphiques contenues dans le fichier XML principal.

La classe Cases sert à dessiner l’échiquier en créant dans la DrawingView un tableau de 64 cases.
La classe Pièce a été déclarée comme une classe abstraite, elle ne peut pas être instanciée. Laclasse Pièce ne donne donc jamais naissance à des objets mais laisse ses classes filles -Pion, Ca-valier, Fou, Tour, Reine, Roi- s’en occuper. Elle ne peut servir qu’à l’héritage.

La classe Pièce est dotée de 3 méthodes principales : SetLegalPositions, isLegal et Move. Set-LegalPositions permet de stocker dans une liste l’ensemble des positions autorisées par la pièce.isLegal renvoie vrai si la nouvelle position définie par le joueur est dans la liste des positionsautorisées de la pièce et faux dans le cas contraire. Move permet de déplacer la pièce vers sanouvelle position et si cette position est déjà occupée, prend la pièce correspondante.

Chaque classe fille -Pion, Cavalier, Fou, Tour, Reine, Roi- hérite de la classe Pièce, c’est-à-direqu’elle va bénéficier de l’ensemble des attributs et méthodes définis dans la classe Pièce. Lesclasses filles peuvent également redéfinir les méthodes de la classe mère. Ce mécanisme de laprogrammation orientée objet est connu sous le nom de polymorphisme. C’est le cas ici avec laméthode setLegalPositions principalement.
La classe CustomDialogClass a pour rôle de permettre l’affichage des fenêtres de début avec lafonction showDialog et de fin avec la fonction showEndGameDialog. Elle est liée à son proprefichier XML qui s’occupe de l’affichage graphique des boutons et des textes.

La classe DrawingView est quant à elle responsable de l’animation graphique et permet l’inter-action avec le joueur. La fonction OnTouchEvent récupère la position du clic de l’utilisateur. Sicette position correspond à une pièce, elle affiche en bleu tous les mouvements possibles pour cette pièce à l’aide de la liste obtenue par la méthode SetLegalPositions. Si la position du clicsuivant correspond à une case colorée, c’est-à-dire si la méthode isLegal renvoie true, la pièceest déplacée vers sa nouvelle position grâce à Move(). Dans le cas contraire, les cases bleuesdisparaissent, le joueur peut alors choisir de cliquer sur une autre pièce.

Après chaque coup, la fonction OnTouchEvent vérifie si le roi n’est pas en position d’échec au-trement dit si le roi n’est pas dans la liste des positions menacées et si c’est le cas, colorie sa caseen rouge. Elle vérifie également si le roi n’est pas en position d’échec et mat en vérifiant s’il n’estpas en position d’échec et qu’il n’y a aucun mouvement possible pour le sauver c’est-à-dire quela taille de la liste possibleMoves générée par SimulAllPos() est nulle.

**3 Diagrammes de fonctionnement**
Diagramme de classe
[ici pour zoomer](https://i.ibb.co/wWVLxgs/Diagramme-de-Classes-3.pn)
![Diagramme de classes](https://i.ibb.co/wWVLxgs/Diagramme-de-Classes-3.png)

Diagramme de séquence : promotion de pion
![Diagramme de séquence : promotion de pion](https://i.ibb.co/fnsfJXj/promotion.png)

**4 Fonctionnalités implémentées**

Lors du démarrage de l’application, une fenêtre s’affiche et demande au joueur s'il veut définir une limite de temps par coup. S'il appuie sur «NON», la fenêtre disparaît. S'il appuie sur «OUI», il a la possibilité de rentrer le nombre de minutes qu'il souhaite. Après avoir appuyé sur «OK» et joué le premier coup, le temps entré par l’utilisateur s’affiche en haut à gauche de l’écran. Le chronomètre se met à jour toutes les secondes et se réinitialise chaque fois qu'un coup est joué.

L’application développée est intuitive et facile à utiliser même pour les débutants aux échecs, car dès que le joueur appuie sur une pièce, les cases autorisées s’affichent en bleu. De même, lorsque le roi est en échec, sa case s’affiche en rouge. Les seuls coups alors permis sont ceux qui mettent le roi en sécurité en plaçant une pièce entre le roi et la pièce qui le menace par exemple, les autres coups sont bloqués. L'application convient également aux joueurs plus expérimentés puisque les coups spéciaux tels que le roque, la promotion et la prise en passant ont été implémentés.

L’application se démarque aussi par son côté ludique. En effet, dès qu'une pièce est déplacée, ou que le jeu est gagné ou perdu, l'application émet un son.

Lorsque la partie est terminée, soit parce que le temps s'est écoulé, soit parce que le roi est en position d'échec et mat ou qu'il y a égalité, une nouvelle fenêtre affiche le gagnant, le temps total écoulé durant la partie et permet de redémarrer le jeu. 
