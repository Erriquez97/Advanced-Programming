import Dictionary
import Data.List
import Data.Char

ciao :: [Char] -> [Char]
ciao word = sort (map toLower word)

-- It reads a text file whose name is passed as argument and returns a new Dictionary after adding
-- each word of the file using its ciao as key .
-- it uses the dictionary like accumulator which at beginning is an empty dictionary
readDict filename= do
    contents <- readFile filename
    let values = lines contents
    return $ foldl (\acc v -> Dictionary.insert acc (ciao v) v) Dictionary.empty values 

-- Function that returns true if the 2 list of keys are equals, otherwise returns false
equalKeys d1 d2 = sort(keys d1) == sort (keys d2)


-- It's a function that that give n a dictionary and a file na me, writes in the file,
-- one per line, each key of the dictionary together with the length of the list of values
-- associated with the key
-- For each key present in the dictionary it creates a string made out of the key plus the length of the list of its values
-- and then it will concatenate these strings in one single string and will write it in the file taken in input
writeDict :: Dictionary String String -> FilePath -> IO ()
writeDict dict filename = do
    let content = concatMap (\k -> case Dictionary.lookup dict k of
          Nothing -> ""
          Just values -> k ++ "->" ++ show (length values) ++ "\n") (keys dict)
    writeFile filename content  
    
main :: IO ()
main = do
    d1 <- readDict "./aux_files/anagram.txt"
    d2 <- readDict "./aux_files/anagram-s1.txt"
    d3 <- readDict "./aux_files/anagram-s2.txt"
    d4 <- readDict "./aux_files/margana2.txt"

    print $ "Dictionary 1 and Dictionary 4 are not equal: " ++ show(d1/=d4)
    print $ "Dictionary 1 have the same keys of Dictionary 4: " ++ show(equalKeys d1 d2)
    print $ "Dictionary 1is equal to Dictionary 2 merged with Dictionary 3: " ++ show(d1== Dictionary.merge d2 d3)

    writeDict d1 "./aux_files/anagout.txt"
    writeDict d4 "./aux_files/gana-out.txt"
