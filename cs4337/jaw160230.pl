oddMultOf3(X) :-
    \+ integer(X),
    !,
    print("not integer"),
    fail.
oddMultOf3(X) :- 3 is X mod 6.



list_prod(l, i).

list_prod([],0).
list_prod([X],X).
list_prod([A|B], Product) :- list_prod(B, C), Product is C * A.

test(X) :- 3 is X mod 6.
test([], [], []).
test(x, y).
test1(X, Y) :- Y is X + 2.

path(fresno,seattle).
path(fresno,albany).
path(fresno,boston).
path(seattle,omaha).
path(seattle,dallas).
path(omaha,albany).
path(omaha,atlanta).
path(albany,seattle).
path(albany,dallas).
path(atlanta,boston).
path(atlanta,dallas).
path(atlanta,albany).
path(dallas,seattle).
path(dallas,albany).
path(c, d).
path(d, e).
path(e, f).
path(f, g).
path(g, h).

route3(A,B,Route) :- direction(A,B,[A],Q), reverse(Q,Route).

direction(A,B,P,[B|P]) :- path(A,B).

direction(A,B,Visited,Route) :- path(A,C), C \== B, \+member(C,Visited), direction(C,B,[C|Visited],Route).


route(A, B, Route) :- route1(A, B, [A], Q), reverse(Q, Route).
route1(A, B, P, [B|P]) :- path(A, B).
route1(A, B, Visit, Route):- path(A, C), C\==B, \+member(C,Visit), direction(C,B,[C|Visit], Route).
route(A, B) :- path(A, X), route(X, B).





segregate(list,list,list).

segregate([],[],[]).
segregate([X|L],[X|L1],L2):-
        0 is mod(X, 2),
        !,
        segregate(L,L1,L2).

segregate([X|L],L1,[X|L2]):-
        segregate(L,L1,L2).
