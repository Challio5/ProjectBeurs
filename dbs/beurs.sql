drop database if exists beurs;
create database beurs;

use beurs;

create table gebruiker (
	gebruikersnummer int not null auto_increment,
	
	gebruikersnaam varchar(30),
	wachtwoord varchar(30),
	naam varchar(30),
	balans double,
	
	primary key(gebruikersnummer)
);

create table vereniging (
	verenigingsnummer int not null auto_increment,
	
	verenigingsnaam varchar(50) not null,

	primary key(verenigingsnummer)
);

create table aandeel (
	aandeelnummer int not null auto_increment,
	
	gebruikersnummer int not null,
	verenigingsnummer int not null,
	aantal int,
	
	primary key(aandeelnummer),
	
	foreign key(gebruikersnummer) references gebruiker(gebruikersnummer),
	foreign key(verenigingsnummer) references vereniging(verenigingsnummer)
);

create table aanbieding (
	aanbiedingnummer int not null auto_increment,

	gebruikersnummer int not null,
	verenigingsnummer int not null,
	aantal int,
	prijs double,
	
	primary key(aanbiedingnummer),
	foreign key(verenigingsnummer) references vereniging(verenigingsnummer), 
	foreign key(gebruikersnummer) references gebruiker(gebruikersnummer) 
);

