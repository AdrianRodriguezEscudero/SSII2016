come(fred,naranjas).
come(fred,filetes).
come(tony,manzanas).
come(john,manzanas).
come(john,uvas).

%¿Que come fred?
%come(fred,X).
%X = naranjas ;
%X = filetes.
%¿Quien come uvas?
%come(X,uvas).
%X = john.
%¿Quien come manzanas?
%come(X,naranjas).
%X = fred.
%¿Quien come manzanas y filetes?
%come(X,manzanas),come(X,filetes).
%false.
%¿Quien come uvas o manzanas o naranjas?
%come(X,uvas);come(Y,manzanas);come(Z,naranjas).
%X = john ;
%Y = tony ;
%Y = john ;
%Z = fred.
%¿Quien come manzanas y uvas?
%come(X,manzanas),come(X,uvas).
%X = john.