DROP SCHEMA IF EXISTS ntdky;
CREATE SCHEMA ntdky;
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

INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, slika, datum, tipKorisnika, blokiran, obrisan) 
VALUES ('adm1', 'adm1', 'Admin', 'Adminic', 'admin1@gmail.com', 'Opis jednog admina', 'adm1', '2018-01-01 12:25:32', 'ADMIN', 0, 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, slika, datum, tipKorisnika, blokiran, obrisan) 
VALUES ('user1', 'user1', '', '', 'user1@gmail.com', 'Opis jednog korisnika', 'user1', '2018-02-12 21:53:01', 'USER', 0, 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, slika, datum, tipKorisnika, blokiran, obrisan) 
VALUES ('user2', 'user2', '', '', 'user2@gmail.com', '', 'user2', '2018-02-12 21:53:47', 'USER', 0, 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, slika, datum, tipKorisnika, blokiran, obrisan) 
VALUES ('user3', 'user3', 'NekoIme3', 'NekoPrezime3', 'user3@yahoo.com', '', '', '2018-02-12 21:56:14', 'USER', 1, 0);
INSERT INTO Korisnik (korisnickoIme, lozinka, ime, prezime, email, opis, slika, datum, tipKorisnika, blokiran, obrisan) 
VALUES ('user4', 'user4', '', '', 'user4@yahoo.com', '', '', '2018-02-13 00:05:02', 'USER', 0, 1);


CREATE TABLE Pretplata(
	ko VARCHAR(16) NOT NULL, 
    koga VARCHAR(16) NOT NULL, 
    FOREIGN KEY(ko) REFERENCES Korisnik(korisnickoIme), 
    FOREIGN KEY(koga) REFERENCES Korisnik(korisnickoIme)
);

INSERT INTO Pretplata(ko, koga) 
VALUES ('adm1', 'user3');
INSERT INTO Pretplata(ko, koga) 
VALUES ('adm1', 'user2');
INSERT INTO Pretplata(ko, koga) 
VALUES ('adm1', 'user1');
INSERT INTO Pretplata(ko, koga) 
VALUES ('user2', 'user1');


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

INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('Ovo je video broj 1', '5aCsNs3eYTE', '1', 'The Mitsubishi Mirage is the worst new car you can buy. Today I\'m reviewing this Mirage to show you the Mitsubishi Mirage is so bad -- and why you should avoid the Mirage at all costs.', 'PUBLIC', 1, 1, 0, 185, '2018-02-12 22:04:58', 'user1', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('Ovo je video za automobil Mazda Miata', 'W-YcpWYAido', '2', 'The Fiat 124 Spider Abarth is a two-seater, rear-wheel drive sports car -- and Fiat\'s take on the Mazda Miata. Today I\'m reviewing the 124 Abarth, and I\'m showing you around the Fiat 124 to show you why the Fiat 124 Spider Abarth isn\'t a better Mazda Miata.', 'PUBLIC', 1, 1, 0, 47, '2018-02-12 22:06:31', 'user1', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('Evo ovo je ferari, on je crven, crvena je... crvena', 'xD0b-GwFRro', '3', 'The Ferrari 250 GT Lusso is one of the most beautiful vintage Ferrari models. Today I’m reviewing this Ferrari Lusso to show you why the 250 Lusso is worth $3 million. ', 'PUBLIC', 1, 1, 1, 9, '2018-02-12 22:07:24', 'user1', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('The Volkswagen Phaeton W12 Was a $120,000 VW Ultra-Luxury Sedan', '1kIbRwNopWw', '4', 'The Volkswagen Phaeton W12 is one of the craziest Volkswagen models ever -- a 12-cylinder Volkswagen luxury sedan with a $100,000 price tag. Today I\'m reviewing the Phaeton to show you why the Volkswagen Phaeton is one of the most interesting cars of our time.', 'PUBLIC', 1, 1, 0, 7, '2018-02-12 22:09:13', 'user2', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('The $230,000 Mercedes-AMG G65 Is the Stupidest Car On Sale', 'f3KjIQCKmM4', '5', 'The Mercedes-AMG G65 is the stupidest car on sale today. The G65 AMG is a $230,000 luxury SUV with a 40-year-old design and a V12 engine -- and the aerodynamics of a file cabinet. I\'m reviewing the Mercedes-Benz G65 AMG and taking you on a tour of the G65.', 'PUBLIC', 1, 1, 0, 13, '2018-02-12 22:10:07', 'user2', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('Here\'s a Tour of a USA-Legal R34 Nissan Skyline GT-R', '4VQIjYBbawE', '6', 'The "R34" Nissan Skyline GT-R is one of the ultimate Japanese cars -- and it\'s the most desirable Nissan model ever built. My entire life, I\'ve dreamt of driving the R34 Skyline GT-R -- and today I\'m reviewing this R34 GT-R, which is one of a handful of R34 Nissan Skyline GT-R models legally in the United States.', 'PRIVATE', 1, 1, 0, 8, '2018-02-12 22:10:47', 'user2', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('The Spyker C8 Is the Quirkiest $250,000 Exotic Car in History', 'NKin_i1pcFA', '8', 'I recently drove a Spyker C8 Spyder, which is tremendously quirky, weird, and cool. Today I\'m reviewing the Spyker C8, and I\'m showing you all the quirks and features of the Spyker C8 Spyder.', 'UNLISTED', 1, 1, 0, 29, '2018-02-12 22:32:45', 'user2', 0);
INSERT INTO Video (naziv, putanjaVidea, putanjaSlike, opis, vidljivostVidea, vidljivostKomentari, vidljivostRejting, blokiran, brojPregleda, datum, vlasnik, obrisan) 
VALUES ('Ovo je video korisnika 3 koji je banovan po defaultu', 'eLj9D4vo56Q', '9', 'The 2018 Jeep Wrangler is totally redesigned — even though it looks mostly the same. Today I’m reviewing the new Wrangler to show you why the 2018 Wrangler is the best Jeep Wrangler yet.', 'PUBLIC', 1, 1, 0, 3, '2018-02-12 23:47:51', 'user3', 0);


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

INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Ovaj video je bas super, ne komentarisem ovo samo zato sto sam ga ja okacio! (a mozda i komentarisem)', '2018-02-12 23:28:25', 'user1', 1, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Nije fer komentarisati pozitivno na svoj video! Evo ti dislajk haha.', '2018-02-12 23:32:17', 'user2', 1, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Alo dosta prepiranja! Bice banovanja!', '2018-02-12 23:33:01', 'adm1', 1, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Ovaj snimak je malo bolji, user1 je bas poboljsao kvalitet pravljenja snimaka, cestitke!', '2018-02-13 00:07:27', 'user2', 2, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Zahvaljujem se na pohvalama, bas mi je drago sto vam sve svidja moj novi video', '2018-02-13 00:10:23', 'user1', 2, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Pogledajte i moje ostale videe!', '2018-02-13 00:12:01', 'user1', 2, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Ovo je bas kul komentar...', '2018-02-13 00:12:23', 'user1', 2, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('jeste, bas ti je kul komentar', '2018-02-13 00:23:22', 'user2', 2, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Vidite ljudi ova mecka je bas crna i kockasta, al je bas los auto, moze to i bolje da se ja pitam.', '2018-02-13 00:25:05', 'user2', 5, 0);
INSERT INTO Komentar (sadrzaj, datum, vlasnik, video, obrisan) 
VALUES ('Sta pricas bas je u fulu auto, to ti samo zato sto nemas vec vozis juga. E sirotinjo...', '2018-02-13 00:25:49', 'user1', 5, 0);


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

INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user2', 0, '2018-02-12 23:28:38', 'KOMENTAR', NULL, 1, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 0, '2018-02-12 23:28:48', 'KOMENTAR', NULL, 1, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 0, '2018-02-12 23:30:04', 'VIDEO', 1, NULL, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user2', 0, '2018-02-12 23:30:06', 'VIDEO', 1, NULL, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user1', 1, '2018-02-12 23:30:15', 'VIDEO', 1, NULL, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user3', 0, '2018-02-12 23:31:00', 'KOMENTAR', NULL, 1, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user3', 0, '2018-02-12 23:31:01', 'VIDEO', 1, NULL, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user2', 1, '2018-02-12 23:32:26', 'KOMENTAR', NULL, 2, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user3', 1, '2018-02-12 23:32:34', 'KOMENTAR', NULL, 2, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 1, '2018-02-12 23:32:43', 'KOMENTAR', NULL, 2, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 1, '2018-02-12 23:34:58', 'KOMENTAR', NULL, 3, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user2', 1, '2018-02-13 00:08:06', 'VIDEO', 2, NULL, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user3', 1, '2018-02-13 00:08:09', 'VIDEO', 2, NULL, 1);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user1', 1, '2018-02-13 00:12:26', 'KOMENTAR', NULL, 4, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user2', 1, '2018-02-13 00:12:32', 'KOMENTAR', NULL, 7, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 1, '2018-02-13 00:17:40', 'VIDEO', 2, NULL, 1);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user1', 1, '2018-02-13 00:19:55', 'VIDEO', 2, NULL, 1);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 1, '2018-02-13 00:23:30', 'KOMENTAR', NULL, 7, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user1', 0, '2018-02-13 00:25:54', 'KOMENTAR', NULL, 10, 1);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('user2', 0, '2018-02-13 00:26:01', 'KOMENTAR', NULL, 10, 0);
INSERT INTO Lajk (vlasnik, pozitivan, datum, tip, video, komentar, obrisan) 
VALUES ('adm1', 0, '2018-02-13 00:26:05', 'KOMENTAR', NULL, 10, 0);