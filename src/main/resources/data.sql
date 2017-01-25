insert into users (id, username, password) values (null, 'admin', 'admin');
insert into county_entity (county_id, name) values (null, 'Vilniaus');
insert into county_entity (county_id, name) values (null, 'Kauno');
insert into district_entity (district_id, adress, county_id, name) values (null, 'Gedimino g. 9', 1, 'Centro');
insert into district_entity (district_id, adress, county_id, name) values (null, 'Panerių g. 55', 1, 'Panarų');
insert into district_entity (district_id, adress, county_id, name) values (null, 'Žaliakalnio g. 1', 2, 'Žaliakalnio');
insert into district_entity (district_id, adress, county_id, name) values (null, 'Kalhozo g. 9', 2, 'Kaimo');
insert into district_representative_entity (id, district_id, name, surname) values (null, 1, 'Marek', 'Visluhij');
insert into district_representative_entity (id, district_id, name, surname) values (null, 2, 'Ponas', 'Binas');
insert into district_representative_entity (id, district_id, name, surname) values (null, 3, 'Aliukas', 'Brolis');
insert into district_representative_entity (id, district_id, name, surname) values (null, 4, 'Kaimo', 'Jurgis');
insert into party_entity (party_id, name) values (null, 'Darbo Partija');
insert into party_entity (party_id, name) values (null, 'Valstiečių Partija');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 1, 'Protingas', 'Juozas', 1, 'Petkelis');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 2, 'Balvanas', 'Petras', 1, 'Petravičius');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 1, 'Kietas', 'Antanas', 2, 'Guoga');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 2, 'Minkstas', 'Dainius', 2, 'Zubrus');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 1, 'Mobilus', 'Sonis', 1, 'Eriksonas');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 2, 'Balvanas', 'Eglė', 1, 'Pušaitė');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 1, 'Kietas', 'Goshas', 2, 'Žaibas');
insert into candidate_entity (id, birth_date, county_id, description, name, party_id, surname) values (null, '1969-06-09', 2, 'Minkstas', 'Algis', 2, 'Ramanauskas');


