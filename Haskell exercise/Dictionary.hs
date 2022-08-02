-- data Tree a = Leaf a | Node a (Tree a) (Tree a)

-- Leaf(10)
-- Leaf('a')

-- Node(2, Leaf(1), Leaf(6))


-- empty = Dict []

-- insert (Dict l) k v = ...

-- lookup [] v = Nothing
-- lookup h:t v = if v == h then Just v else lookup t v

-- d1 = empty

-- d1 = insert d1 10 'a'
-- d1 = insert d1 12, 'b'
-- d1

data Dictionary key values = Dict [(key, [values])]  deriving (Show, Eq)

empty = Dict []
-- insert list key values = list ++ [(key, values)]

-- _insert Dict [] = Nothing
-- _insert Dict ((k, []):xs) Just k
-- _insert Dict ((k, v1:vl):xs) s = Just k + v1


-- (Dict((k,v:vs):xs)) a b

-- Inserimento
-- insert (Dict []) a b = Empty

insert (Dict ls) a b =  Dict (insert' ls a b)

insert' [] a b = [(a,[b])]
insert' ((k,v):xs) a b = if a == k then ((k, b:v):xs) else (k,v): insert' xs a b



-- (Dict [(20,[43,66]),(32,[5,97])])

-- Ricerca 

-- lookup' (Dict [] ) key= Nothing
lookup' (Dict ls) key = (search ls key)

search key [] = Nothing
search key ((k,v):xs) = if key == k then Just v else search' xs key


-- Lista chiavi

chiavi (Dict ls) = chiavi' ls

chiavi' [] = []
chiavi' (x:xs) = fst x: chiavi' xs 


-- Lista valori

valori (Dict ls) = valori' ls

valori' [] = []
valori' (x:xs) = snd x ++ valori' xs


-- Merge 2 dictionaries 

merge (Dict ls1) (Dict ls2) = Dict ( (intersection ls1 ls2) ++ (rest ls1 ls2) )
 where
    intersection ((k,vs):xs) ((a,bs):cs) =
        case  search k ((a,bs):cs)  of Nothing -> ((k,vs): intersection xs ((a,bs):cs)) 
                                       Just v -> ((k,vs ++ v): intersection xs ((a,bs):cs))
    intersection [] ((a,bs):cs) = []

    rest lista1 ((a,bs):cs) =
        case search a (intersection lista1 ((a,bs):cs)) of Nothing -> ((a,bs): rest (intersection lista1 ((a,bs):cs)) cs) 
                                                           Just _ -> rest (intersection lista1 ((a,bs):cs)) cs
    rest lista1 [] = []

instance Eq (Dictionary  d1) where

  d1 == d1 = funzione d1 d2

funzione (Dict ls1) (Dict ls2) = uguali ls1 ls2

uguali ((k,vs):xs) ((a,bs):cs) = case search k ((a,bs):cs) of Nothing -> false
                                                              Just v -> search 




main :: IO ()
main =
  do
    print (insert empty 5 6)




















-- Equality
-- uguale (Dict ls1) (Dict ls2) = uguali' ls1 ls2
-- uguali' ((x,v1:v1s):xs) ((y,v2:v2s):vs) = ((x,v1:v1s):xs) == ((y,v2:v2s):vs)

-- checkLista (x:xs) (y:ys) = (x:xs) = (y:ys)






-- insert ((k, v:vs):xs)) a b = Dict [(a, [b])]
-- insert (Dict ((a, x:xs):ds)) k v = Dict ((a, v:x:xs):ds)

-- insert dict l key values = dict ++ [(key,values)]
