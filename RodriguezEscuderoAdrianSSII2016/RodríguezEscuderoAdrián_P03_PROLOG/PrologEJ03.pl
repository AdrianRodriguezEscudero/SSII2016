likes(john,mary).
likes(john,trenes).
likes(peter,coches).
likes(Person1,Person2):-
hobby(Person1,Hobby),
hobby(Person2,Hobby).

hobby(john,mirar_trenes).
hobby(tim,navanegar).
hobby(helen,mirar_trenes).
hobby(simon,navegar).