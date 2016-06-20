The central idea behind my approach is noise channel model.

One possible alternative approach could be to finding words with the minimum Damerau–Levenshtein distance with words, coming from real query logs. The submission of the project  would be impossible in that case - i can’t attach a few gigabytes of data.

To build noisy channel model, i used an approach from Eric Brill and Robert C. Moore “An Improved Error Model for Noisy Channel Spelling Correction”. 

As training data set, i used two small data sets, coming from Peter Norvig spell correction and GNU Aspell. 

The benefit of this approach is that we can model the sequence of characters and the mis-typings.

The major drawback is the data set by itself. We are targeting mobile devices with their tiny keyboard, and I am certain that typos will have differences. 

Another drawback is lack of real big enough data set, to be able to estimate the probability of each typo. We either need a dump from a search engine ( which I did for AOL data set, yet it would get too involved for this short homework to work with it ), or massive dump of tweets ( which is even better, cause we would have real typos from mobile keyboards). 

text input file and two training files are located in “noisyChannelData” directory.
After you unpack the project - make according modifications to the path of that directory. 

prefixL - is the parameter, which controls the window size for sequence matching between pairs of “correctly typed” and “word with typos”. 


“PhraseProcessor phrase = new PhraseProcessor(5, patternL, model)” the first parameter - is the number of typos you want to see for each input sentence