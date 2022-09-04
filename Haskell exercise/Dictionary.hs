module Dictionary (
    Dictionary,     
    Dictionary.empty,
    Dictionary.insert,
    Dictionary.lookup,
    Dictionary.keys,
    Dictionary.values,
    Dictionary.merge
) where

data Dictionary key values = Dict [(key, [values])]  deriving (Show)


-- It returns an empty dictionary
empty :: Dictionary key values
empty = Dict []

-- It returns a dictionary obtained by inserting in dict the element k with its value v

insert :: Eq key => Dictionary key values -> key -> values -> Dictionary key values
insert (Dict ls) a b =  Dict (insert' ls a b)

insert' :: Eq t1 => [(t1, [t2])] -> t1 -> t2 -> [(t1, [t2])]
insert' [] a b = [(a,[b])]
insert' ((k,v):xs) a b = if a == k then ((k, b:v):xs) else (k,v): insert' xs a b

-- It returns a value of type Maybe [b] , which is the list of elements with key k , if such list exists in dict and Nothing otherwise

lookup :: Eq t => Dictionary t values -> t -> Maybe [values]
lookup (Dict ls) key = (search key ls)

search :: Eq t => t -> [(t, a)] -> Maybe a
search key [] = Nothing
search key ((k,v):xs) = if key == k then Just v else search key xs


-- It returns a list containing the keys of dict .

keys :: Dictionary a values -> [a]
keys (Dict ls) = keys' ls

keys' :: [(a, b)] -> [a]
keys' [] = []
keys' (x:xs) = fst x: keys' xs 


-- It returns a single list containing all the values present in dict .

values :: Dictionary a1 a2 -> [a2]
values (Dict ls) = values' ls

values' :: [(a1, [a2])] -> [a2]
values' [] = []
values' (x:xs) = snd x ++ values' xs


-- It returns the Dictionary obtained by merging the contents of the two dictionaries 
-- The intersection function takes the two lists to be merged and check if each element in the first list is present in
-- the second list.This check returns nothing if the element is not present and in this case we call in recursive way the function intersection
-- on the rest of the elements of the first list and the second list. In case the element is found we add the values of the element present
-- in the second list to the values of the element in the first list. So at the end of intersection function we will have a list made out of 
-- the first list taken in input, but this list now will contain also the values of the elements that were found in the second list.
-- The rest function is needed because we need to merge all the keys/values that are not presents in the first list, so we do the search of each
-- element present in the second list in the list 1, in case this search returns an element we don't do anything because that element has been already
-- added in the function intersection, if instead the element in the second list is not found in the first list then we add this element with its values to the list
-- The merge function will just concatenate the list returned by the intersection function and the list returned by the rest function.

merge :: Eq key =>Dictionary key values-> Dictionary key values -> Dictionary key values
merge (Dict ls1) (Dict ls2) = Dict ( (intersection ls1 ls2) ++ (rest ls1 ls2) )
 where
    intersection ((k,vs):xs) ((a,bs):cs) =
        case  search k ((a,bs):cs)  of Nothing -> ((k,vs): intersection xs ((a,bs):cs)) 
                                       Just v -> ((k,vs ++ v): intersection xs ((a,bs):cs))
    intersection [] ((a,bs):cs) = []

    rest list1 ((a,bs):cs) =
        case search a (intersection list1 ((a,bs):cs)) of Nothing -> (a,bs): rest (intersection list1 ((a,bs):cs)) cs
                                                          Just _ -> rest (intersection list1 ((a,bs):cs)) cs
    rest list1 [] = []


-- It checks if two dictionaries are equals

instance (Eq a, Eq b) => Eq (Dictionary a b) where
    (==) d1 d2 = 
        let kd1 = keys d1 in -- The set of keys in the first dictionary
        let kd2 = keys d2 in -- The set of keys in the second dictionary
        equals kd1 kd2 && equals_values d1 d2 kd1 kd2

        where
            -- Returns true if x is contained in y
            contains [] y = True
            contains (x:xs) y = elem x y && contains xs y

            -- Two lists are equals if x is contained in y and y is contained in x
            equals x y = contains x y && contains y x 

            -- Returns true if d1 has the same elements of d2
            -- This function assumes that the two lists of keys are equals
            equals_values d1 d2 [] kd2 = True
            equals_values d1 d2 (x:xs) kd2 =
                case Dictionary.lookup d1 x of -- It returns the list of the values of the key x that belong to the dictionary d1
                    Nothing -> False 
                    Just valuesd1 -> 
                        case Dictionary.lookup d2 x of -- It returns the list of the values of the key x that belong to the dictionary d2
                            Nothing -> False
                            Just valuesd2 -> equals valuesd1 valuesd2 && equals_values d1 d2 xs kd2 -- It checks if the lsts of values are equals and then calls recursively the function equalsvalues passing the rest of the keys