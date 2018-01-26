DROP SCHEMA IF EXISTS ntdky;
CREATE SCHEMA ntdky DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE ntdky;

CREATE TABLE Korisnik (
	korisnickoIme VARCHAR(16) NOT NULL, 
	lozinka VARCHAR(16) NOT NULL, 
    ime VARCHAR(16), 
    prezime VARCHAR(16), 
    email VARCHAR(30) NOT NULL, 
    opis VARCHAR(1024), 
    slika VARCHAR(128), 
    datum DATETIME NOT NULL, #YYYY-MM-DD HH:MM:SS
	tipKorisnika ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER', 
    blokiran BIT NOT NULL DEFAULT 0, 
    obrisan BIT DEFAULT 0, 
    
    PRIMARY KEY(korisnickoIme)
);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran) 
VALUES ('adm1', 'adm1', 'Admin', 'Adminic', 'admin1@gmail.com', 'Opis jednog admina', '2018-01-01 12:25:32', 'ADMIN', 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran) 
VALUES ('adm2', 'adm2', '', '', 'admin2@gmail.com', '', '2018-01-03 08:45:11', 'ADMIN', 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran) 
VALUES ('user1', 'user1', 'Korisnik', 'Korisnikic', 'user1@gmail.com', 'Opis jednog korisnika', '2018-01-04 11:12:35', 'USER', 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran) 
VALUES ('user2', 'user2', '', '', 'user2@gmail.com', '', '2018-01-05 22:05:02', 'USER', 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, datum, tipKorisnika, blokiran) 
VALUES ('user3', 'user3', '', '', 'user3@gmail.com', '', '2018-01-06 17:27:52', 'USER', 1);

CREATE TABLE Pretplata(
	ko VARCHAR(16) NOT NULL, 
    koga VARCHAR(16) NOT NULL, 
    FOREIGN KEY(ko) REFERENCES Korisnik(korisnickoIme), 
    FOREIGN KEY(koga) REFERENCES Korisnik(korisnickoIme)
);
INSERT INTO Pretplata(ko, koga) 
VALUES ('user1', 'user2');
INSERT INTO Pretplata(ko, koga) 
VALUES ('user1', 'user3');
INSERT INTO Pretplata(ko, koga) 
VALUES ('user2', 'user3');

CREATE TABLE Video (
	id BIGINT AUTO_INCREMENT, 
	naziv VARCHAR(80) NOT NULL, 
    putanjaVidea VARCHAR(20) NOT NULL, 
    putanjaSlike VARCHAR(20) NOT NULL, 
    opis VARCHAR(1024), 
    vidljivostVidea ENUM('PUBLIC', 'UNLISTED', 'PRIVATE') NOT NULL DEFAULT 'PRIVATE', 
    vidljivostKomentari BIT NOT NULL DEFAULT 0, 
    vidljivostRejting BIT NOT NULL DEFAULT 0, 
    blokiran BIT NOT NULL DEFAULT 0, 
    brojPregleda BIGINT DEFAULT 0, 
    datum DATETIME NOT NULL, #YYYY-MM-DD HH:MM:SS
    vlasnik VARCHAR(16) NOT NULL, 
    obrisan BIT DEFAULT 0, 
    
    PRIMARY KEY(id), 
    FOREIGN KEY(vlasnik) REFERENCES Korisnik(korisnickoIme)
);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, datum, vlasnik) 
VALUES ('The Fiat 124 Abarth Isnt a Better Mazda Miata', 'W-YcpWYAido', 'W-YcpWYAido.jpg', '', 'PUBLIC', 1, 1, 0, '2018-01-07 12:23:56', 'user1');
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, datum, vlasnik) 
VALUES ('The Mitsubishi Mirage Is the Worst New Car You Can Buy', '5aCsNs3eYTE', '5aCsNs3eYTE.jpg', 'Opis1', 'PUBLIC', 1, 1, 0, '2018-01-08 11:13:42', 'user1');
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, datum, vlasnik) 
VALUES ('Heres a Tour of a USA-Legal R34 Nissan Skyline GT-R', '4VQIjYBbawE', '4VQIjYBbawE.jpg', '', 'UNLISTED', 1, 1, 0, '2018-01-09 08:03:18', 'user2');
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, datum, vlasnik) 
VALUES ('The $230,000 Mercedes-AMG G65 Is the Stupidest Car On Sale', 'f3KjIQCKmM4', 'f3KjIQCKmM4.jpg', 'Opis2', 'PRIVATE', 1, 1, 0, '2018-01-10 23:23:23', 'user2');

CREATE TABLE Komentar (
	id BIGINT AUTO_INCREMENT, 
	sadrzaj VARCHAR(1024) NOT NULL, 
    datum DATETIME NOT NULL, 
    vlasnik VARCHAR(16) NOT NULL, 
    video BIGINT NOT NULL, 
    obrisan BIT DEFAULT 0, 
    
    PRIMARY KEY(id), 
    FOREIGN KEY(vlasnik) REFERENCES Korisnik(korisnickoIme), 
    FOREIGN KEY(video) REFERENCES Video(id)
);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video) 
VALUES ('Ovo je komentar 1', '2018-01-11 09:32:12', 'user1', 1);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video) 
VALUES ('Ovo je komentar 2', '2018-01-11 14:12:57', 'user2', 1);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video) 
VALUES ('Ovo je komentar 3', '2018-01-11 16:15:51', 'user3', 1);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video) 
VALUES ('Ovo je komentar 4', '2018-01-11 19:29:36', 'user1', 2);

CREATE TABLE Lajk (
	id BIGINT AUTO_INCREMENT, 
    vlasnik VARCHAR(16) NOT NULL, 
    pozitivan BIT NOT NULL, 
    datum DATETIME NOT NULL, 
    tip ENUM('VIDEO', 'KOMENTAR') NOT NULL, 
    video BIGINT, 
    komentar BIGINT, 
    obrisan BIT DEFAULT 0, 
    
    PRIMARY KEY(id), 
    FOREIGN KEY(vlasnik) REFERENCES Korisnik(korisnickoIme), 
    FOREIGN KEY(video) REFERENCES Video(id), 
    FOREIGN KEY(komentar) REFERENCES Komentar(id)
);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video) 
VALUES ('user1', 1, '2018-01-12 07:14:32', 'VIDEO', 2);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, komentar) 
VALUES ('user2', 0, '2018-01-12 20:41:21', 'KOMENTAR', 3);
