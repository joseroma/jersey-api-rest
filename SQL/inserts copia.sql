

insert into filmAffinity3.PELICULA VALUES(1, "Bohemian Rhapsody", "http://es.web.img3.acsta.net/r_1280_720/pictures/18/05/21/12/50/5763573.jpg", "2018-11-2","Drama-Biografía", "United Kingdom", "This is the story of how Freddie Mercury and his fellow band members Brian May, Roger Taylor and John De ....");
insert into filmAffinity3.PELICULA VALUES(2, "Pulp Fiction", "https://pics.filmaffinity.com/pulp_fiction-210382116-mmed.jpg", "1994-1-1","Thriller", "United States", "This is the story of how Freddie Mercury and his fellow band members Brian May, Roger Taylor and John De ....");
insert into filmAffinity3.PELICULA VALUES(3,"Millennium: Lo que no te mata te hace más fuerte","https://pics.filmaffinity.com/the_girl_in_the_spider_s_web-235890631-large.jpg","2018-2-1","Thriller. Drama. Intriga | Crimen","Estados Unidos","La joven hacker Lisbeth Salander y el periodista Mikael...");
insert into filmAffinity3.PELICULA VALUES(4, "The good Father", "https://pics.filmaffinity.com/the_godfather-529853536-mmed.jpg", "1972-1-1", "Drama", "United States", "Epic tale of a 1940s New York Mafia family and their struggle to protect their empire from rival families as the leadership switche...");
insert into filmAffinity3.PELICULA VALUES(5,"Blade Runner 2049","https://pics.filmaffinity.com/blade_runner_2049-681477614-large.jpg","2017-1-1","Ciencia ficción","Estados Unidos","Treinta años después de los eventos del primer film, un ...");
insert into filmAffinity3.PELICULA VALUES(6, "Fight Club", "https://pics.filmaffinity.com/fight_club-320750671-mmed.jpg", "1999-1-1", "Drama. Thriller", "United States", "A nameless disillusioned young urban male (Edward Norton) fights insomnia..");
insert into filmAffinity3.PELICULA VALUES(7, "The Shawshank Redemption", "https://pics.filmaffinity.com/the_shawshank_redemption-576140557-mmed.jpg", "1994-2-1", "Drama.Prision Drama", "United States", "Andy Dufresne (Tim Robbins) is sentenced to two consecutive life terms in prison for the murders of his wife ...");
insert into filmAffinity3.PELICULA VALUES(8,"Dunkerque","https://pics.filmaffinity.com/dunkirk-461720087-large.jpg","2017-2-1","Bélico. Drama | II Guerra Mundial","Estados Unidos", "Año 1940, en plena 2ª Guerra Mundial..... ");
insert into filmAffinity3.PELICULA VALUES(9,"La ciudad de las estrellas (La La Que no gano)","https://pics.filmaffinity.com/la_la_land-262021831-large.jpg","2016-1-1","Musical. Romance. Comedia.","Estados Unidos", "Mia (Emma Stone), una joven aspirante a actriz que trabaja como camarera mientras...");
insert into filmAffinity3.PELICULA VALUES(10, "El rey león","https://pics.filmaffinity.com/the_lion_king-983881776-large.jpg","1994-1-1","Animación. Drama. Aventuras","Estados Unidos","La sabana africana es el escenario en el que tienen lugar las aventuras de Simba, un pequeño león que es ...");


select * from filmAffinity3.PELICULA;


#PELICULA 1
		#actores de pelicula 1
insert into filmAffinity3.ACTOR VALUES(11, "Rami", "Manek" , "1960-1-1");
insert into filmAffinity3.ACTOR VALUES(12, "Josephi", "Mazzello" , "1962-1-1");
		#relacionar actores con película
insert into filmAffinity3.PELICULA_has_ACTOR VALUES(1,11);
insert into filmAffinity3.PELICULA_has_ACTOR VALUES(1,12);
		#director de pelicula 1
insert into filmAffinity3.DIRECTOR VALUES(100, "Bryan Singer");
		#relacionar actor con pelicula
insert into filmAffinity3.PELICULA_has_DIRECTOR VALUES(1,100);

#PELICULA 2 (mismos actores que 1 y otr más)

insert into filmAffinity3.ACTOR VALUES(21, "Uma", "Thruman" , "1960-10-2");
insert into filmAffinity3.ACTOR VALUES(22, "Roger", "Taylor" , "1958-11-3");

insert into filmAffinity3.PELICULA_has_ACTOR VALUES(2,11);
insert into filmAffinity3.PELICULA_has_ACTOR VALUES(2,12);
insert into filmAffinity3.PELICULA_has_ACTOR VALUES(2,21);
insert into filmAffinity3.PELICULA_has_ACTOR VALUES(2,22);

insert into filmAffinity3.DIRECTOR VALUES(200, "Quentin Tarantino");
insert into filmAffinity3.PELICULA_has_DIRECTOR VALUES(2,200);

#PELICULA 3
insert into filmAffinity3.ACTOR VALUES(31, "Lance", "Henriksen" , "1954-3-7");
insert into filmAffinity3.ACTOR VALUES(32, "Megan", "Gallagher" , "1962-7-13");

insert into filmAffinity3.PELICULA_has_ACTOR VALUES(3,31);
insert into filmAffinity3.PELICULA_has_ACTOR VALUES(3,32);

insert into filmAffinity3.PELICULA_has_ACTOR VALUES(3,22);

#---
select * from filmAffinity3.DIRECTOR;

select * from filmAffinity3.PELICULA_has_DIRECTOR ;
select * from filmAffinity3.ACTOR;
select * from filmAffinity3.PELICULA_has_ACTOR ;







