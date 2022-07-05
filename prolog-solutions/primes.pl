isDivided(A,B):- 0 is mod(A,B).
good(N, D) :- Square is D * D, Square > N,!.
good(N, D) :- \+ isDivided(N,D), D1 is D + 1, good(N, D1).
prime(N) :- N > 1, good(N, 2).
composite(N) :- \+ prime(N).
next(N, N1) :- N1 is N + 1, prime(N1), !.
next(N, N1) :- N0 is N + 1, next(N0, N1).
prime_divisors(N, V) :- fill(N, 2, V).
fill(1, _, []).
fill(N, D, V) :- isDivided(N,D), prime(D), V = [D|V1], Divide is N / D, fill(Divide, D, V1).
fill(N, D, V) :- \+ isDivided(N,D), next(D, D1),D1 =< N, fill(N, D1, V).
contains(V, [V | _]).
contains(V, [_ | T]) :- contains(V, T).
unique_prime_divisors(N, V) :- fill(N, 2, V1), remove_duplicates(V1,V).
remove_duplicates([], []).
remove_duplicates([H | T], R) :-
    contains(H, T), !,
    remove_duplicates(T, R).
remove_duplicates([H | T], [H | R]) :-
    remove_duplicates(T, R).