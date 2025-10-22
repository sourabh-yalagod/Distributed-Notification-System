Imagine building a movie recommendation app that goes beyond keyword matching. Your users get intuitive, meaningful suggestions based on the true meaning of movie plots — powered by semantic understanding.

### Store movie documents with vector embeddings
Semantic search uses vector embeddings — numeric representations of text that capture meaning, enabling search by intent rather than keywords.

You'll import a dataset of plot summaries, each paired with an embedding vector.

Each movie is stored as a JSON document with:
  - `title`, `genres`, `year`, `plot`
  - `embedding`: a binary-encoded `FLOAT32[]` for vector similarity, generated via sentence-transformer models or similar.

```redis:[run_confirmation=true] Upload Movies
// Demo uses 8 DIM embeddings; production typically uses 128–1536D.
JSON.SET movie:001 $ '{"title":"Toy Story","genres":["Animation","Comedy","Family"],"plot":"Toys come to life when humans arent around.","year":1995,"embedding":[0.22,0.04,0.33,0.12,-0.02,0.17,0.09,0.01]}'
JSON.SET movie:002 $ '{"title":"Inside Out","genres":["Animation","Comedy","Drama"],"plot":"Emotions guide a young girl through change.","year":2015,"embedding":[0.20,0.03,0.31,0.11,-0.03,0.16,0.08,0.02]}'
JSON.SET movie:003 $ '{"title":"Whiplash","genres":["Drama","Music"],"plot":"A young drummer is pushed to greatness.","year":2014,"embedding":[0.14,0.01,0.22,0.08,-0.07,0.10,0.04,0.00]}'
JSON.SET movie:004 $ '{"title":"La La Land","genres":["Drama","Music","Romance"],"plot":"A jazz musician falls in love in LA.","year":2016,"embedding":[0.15,0.03,0.23,0.09,-0.08,0.14,0.06,0.01]}'
JSON.SET movie:005 $ '{"title":"The Matrix","genres":["Action","Sci-Fi"],"plot":"A hacker discovers reality is a simulation.","year":1999,"embedding":[0.12,-0.03,0.25,0.04,-0.10,0.09,0.05,-0.02]}'
JSON.SET movie:006 $ '{"title":"Inception","genres":["Action","Adventure","Sci-Fi"],"plot":"A thief steals information through dreams.","year":2010,"embedding":[0.14,-0.01,0.27,0.06,-0.09,0.10,0.04,-0.03]}'
JSON.SET movie:007 $ '{"title":"Tenet","genres":["Action","Sci-Fi","Thriller"],"plot":"Time-inversion to prevent World War III.","year":2020,"embedding":[0.13,-0.06,0.29,0.05,-0.11,0.12,0.06,-0.01]}'
JSON.SET movie:008 $ '{"title":"Finding Nemo","genres":["Animation","Adventure","Family"],"plot":"A clownfish searches for his son.","year":2003,"embedding":[0.18,0.02,0.30,0.10,-0.05,0.15,0.07,0.01]}'
JSON.SET movie:009 $ '{"title":"Coco","genres":["Animation","Family","Music"],"plot":"A boy enters the Land of the Dead.","year":2017,"embedding":[0.21,0.04,0.34,0.13,-0.02,0.19,0.10,0.02]}'
JSON.SET movie:010 $ '{"title":"Soul","genres":["Animation","Adventure","Comedy"],"plot":"A jazz musician explores the afterlife.","year":2020,"embedding":[0.16,0.02,0.28,0.10,-0.06,0.13,0.07,0.00]}'
JSON.SET movie:011 $ '{"title":"The Dark Knight","genres":["Action","Crime","Drama"],"plot":"Batman fights the Joker.","year":2008,"embedding":[0.12,-0.03,0.25,0.04,-0.09,0.10,0.05,-0.02]}'
JSON.SET movie:012 $ '{"title":"Frozen","genres":["Animation","Adventure","Comedy"],"plot":"A princess sets off to find her sister.","year":2013,"embedding":[0.22,0.04,0.33,0.12,-0.03,0.18,0.10,0.02]}'
JSON.SET movie:013 $ '{"title":"The Lion King","genres":["Animation","Adventure","Drama"],"plot":"A lion prince flees and returns.","year":1994,"embedding":[0.19,0.02,0.32,0.10,-0.04,0.18,0.09,0.03]}'
JSON.SET movie:014 $ '{"title":"Shrek","genres":["Animation","Adventure","Comedy"],"plot":"An ogre rescues a princess.","year":2001,"embedding":[0.21,0.03,0.32,0.11,-0.04,0.17,0.09,0.01]}'
JSON.SET movie:015 $ '{"title":"The Social Network","genres":["Biography","Drama"],"plot":"The rise of Facebook and its creator.","year":2010,"embedding":[0.10,-0.01,0.21,0.05,-0.07,0.06,0.03,-0.02]}'
JSON.SET movie:016 $ '{"title":"Guardians of the Galaxy","genres":["Action","Adventure","Sci-Fi"],"plot":"A group of intergalactic criminals must save the universe.","year":2014,"embedding":[0.13,0.00,0.28,0.07,-0.08,0.11,0.05,-0.01]}'
JSON.SET movie:017 $ '{"title":"Moana","genres":["Animation","Adventure","Family"],"plot":"A young girl sets sail to save her island.","year":2016,"embedding":[0.20,0.03,0.33,0.12,-0.03,0.17,0.09,0.02]}'
JSON.SET movie:018 $ '{"title":"Whale Rider","genres":["Drama","Family"],"plot":"A girl fights tradition to become chief.","year":2002,"embedding":[0.15,0.01,0.25,0.09,-0.05,0.12,0.06,0.00]}'
JSON.SET movie:019 $ '{"title":"Rocketman","genres":["Biography","Drama","Music"],"plot":"The story of Elton Johns breakthrough years.","year":2019,"embedding":[0.14,0.01,0.22,0.07,-0.06,0.11,0.05,0.01]}'
JSON.SET movie:020 $ '{"title":"Amadeus","genres":["Biography","Drama","Music"],"plot":"The rivalry between Mozart and Salieri.","year":1984,"embedding":[0.13,0.00,0.20,0.06,-0.07,0.10,0.04,0.00]}'
JSON.SET movie:021 $ '{"title":"The Sound of Music","genres":["Biography","Drama","Music"],"plot":"A governess brings music to a family.","year":1965,"embedding":[0.14,0.02,0.21,0.07,-0.06,0.11,0.05,0.01]}'
JSON.SET movie:022 $ '{"title":"Les Miserables","genres":["Drama","Music","Romance"],"plot":"The struggles of ex-convict Jean Valjean.","year":2012,"embedding":[0.13,0.01,0.23,0.08,-0.07,0.12,0.05,0.01]}'
JSON.SET movie:023 $ '{"title":"The Greatest Showman","genres":["Biography","Drama","Music"],"plot":"The story of P.T. Barnum and his circus.","year":2017,"embedding":[0.15,0.03,0.25,0.09,-0.05,0.13,0.06,0.02]}'
JSON.SET movie:024 $ '{"title":"A Star Is Born","genres":["Drama","Music","Romance"],"plot":"A musician helps a young singer find fame.","year":2018,"embedding":[0.14,0.02,0.24,0.08,-0.06,0.12,0.05,0.01]}'
JSON.SET movie:025 $ '{"title":"Mad Max: Fury Road","genres":["Action","Adventure","Sci-Fi"],"plot":"In a post-apocalyptic wasteland, Max helps rebels escape.","year":2015,"embedding":[0.11,-0.02,0.26,0.05,-0.10,0.08,0.05,-0.02]}'
JSON.SET movie:026 $ '{"title":"Blade Runner 2049","genres":["Sci-Fi","Thriller"],"plot":"A new blade runner uncovers secrets.","year":2017,"embedding":[0.12,-0.03,0.27,0.06,-0.09,0.09,0.06,-0.01]}'
JSON.SET movie:027 $ '{"title":"Arrival","genres":["Drama","Sci-Fi","Thriller"],"plot":"A linguist communicates with aliens.","year":2016,"embedding":[0.13,-0.01,0.28,0.07,-0.08,0.11,0.05,-0.01]}'
JSON.SET movie:028 $ '{"title":"Interstellar","genres":["Adventure","Drama","Sci-Fi"],"plot":"Explorers travel through a wormhole in space.","year":2014,"embedding":[0.14,-0.02,0.29,0.08,-0.09,0.12,0.06,-0.02]}'
JSON.SET movie:029 $ '{"title":"E.T. the Extra-Terrestrial","genres":["Family","Sci-Fi"],"plot":"A boy befriends an alien.","year":1982,"embedding":[0.17,0.01,0.31,0.10,-0.06,0.15,0.07,0.01]}'
JSON.SET movie:030 $ '{"title":"The Avengers","genres":["Action","Adventure","Sci-Fi"],"plot":"Superheroes team up to save the world.","year":2012,"embedding":[0.13,0.00,0.27,0.07,-0.08,0.11,0.06,-0.01]}'
JSON.SET movie:031 $ '{"title":"Guardians of the Galaxy Vol. 2","genres":["Action","Adventure","Comedy"],"plot":"The Guardians fight to protect the galaxy.","year":2017,"embedding":[0.15,0.01,0.28,0.09,-0.07,0.13,0.07,0.01]}'
JSON.SET movie:032 $ '{"title":"Up","genres":["Animation","Adventure","Comedy"],"plot":"An old man goes on an adventure in his flying house.","year":2009,"embedding":[0.21,0.04,0.32,0.11,-0.04,0.16,0.09,0.02]}'
JSON.SET movie:033 $ '{"title":"Zootopia","genres":["Animation","Adventure","Comedy"],"plot":"A bunny cop solves a mystery in a city of animals.","year":2016,"embedding":[0.20,0.03,0.31,0.10,-0.05,0.15,0.08,0.01]}'
JSON.SET movie:034 $ '{"title":"Big Hero 6","genres":["Animation","Action","Comedy"],"plot":"A robotics prodigy teams with friends to fight crime.","year":2014,"embedding":[0.19,0.02,0.30,0.09,-0.05,0.14,0.08,0.01]}'
JSON.SET movie:035 $ '{"title":"The Prestige","genres":["Drama","Mystery","Sci-Fi"],"plot":"Two magicians engage in a deadly rivalry.","year":2006,"embedding":[0.12,-0.02,0.24,0.06,-0.08,0.10,0.05,-0.01]}'
JSON.SET movie:036 $ '{"title":"Dunkirk","genres":["Action","Drama","History"],"plot":"Allied soldiers are evacuated during WWII.","year":2017,"embedding":[0.10,-0.03,0.22,0.05,-0.09,0.07,0.04,-0.02]}'
JSON.SET movie:037 $ '{"title":"Jumanji: Welcome to the Jungle","genres":["Action","Adventure","Comedy"],"plot":"Teens trapped in a video game jungle.","year":2017,"embedding":[0.16,0.01,0.27,0.08,-0.06,0.12,0.06,0.01]}'
JSON.SET movie:038 $ '{"title":"Cinderella","genres":["Animation","Family","Fantasy"],"plot":"A young girl overcomes her cruel stepmother.","year":1950,"embedding":[0.19,0.03,0.31,0.11,-0.04,0.16,0.08,0.02]}'
JSON.SET movie:039 $ '{"title":"Mulan","genres":["Animation","Adventure","Drama"],"plot":"A young woman disguises as a soldier.","year":1998,"embedding":[0.20,0.03,0.32,0.11,-0.04,0.17,0.09,0.02]}'
JSON.SET movie:040 $ '{"title":"Beauty and the Beast","genres":["Animation","Family","Fantasy"],"plot":"A young woman falls in love with a beast.","year":1991,"embedding":[0.18,0.02,0.30,0.10,-0.05,0.15,0.08,0.01]}'
JSON.SET movie:041 $ '{"title":"The Godfather","genres":["Crime","Drama"],"plot":"The aging patriarch of an organized crime dynasty transfers control to his son.","year":1972,"embedding":[0.11,-0.04,0.24,0.06,-0.10,0.07,0.05,-0.03]}'
JSON.SET movie:042 $ '{"title":"Pulp Fiction","genres":["Crime","Drama"],"plot":"The lives of two mob hitmen, a boxer, and others intertwine.","year":1994,"embedding":[0.12,-0.03,0.23,0.07,-0.09,0.09,0.04,-0.01]}'
JSON.SET movie:043 $ '{"title":"Forrest Gump","genres":["Drama","Romance"],"plot":"The presidencies of Kennedy and Johnson through the eyes of Forrest.","year":1994,"embedding":[0.14,0.01,0.26,0.08,-0.07,0.11,0.06,0.01]}'
JSON.SET movie:044 $ '{"title":"Gladiator","genres":["Action","Drama"],"plot":"A former Roman General seeks revenge.","year":2000,"embedding":[0.13,0.00,0.25,0.07,-0.08,0.10,0.05,0.00]}'
JSON.SET movie:045 $ '{"title":"Titanic","genres":["Drama","Romance"],"plot":"A seventeen-year-old aristocrat falls in love with a kind but poor artist.","year":1997,"embedding":[0.15,0.02,0.28,0.09,-0.06,0.13,0.06,0.01]}'
JSON.SET movie:046 $ '{"title":"Jurassic Park","genres":["Adventure","Sci-Fi"],"plot":"Scientists clone dinosaurs for a theme park.","year":1993,"embedding":[0.14,-0.01,0.26,0.08,-0.07,0.11,0.06,0.00]}'
JSON.SET movie:047 $ '{"title":"The Shawshank Redemption","genres":["Drama"],"plot":"Two imprisoned men bond over a number of years.","year":1994,"embedding":[0.15,0.00,0.27,0.09,-0.06,0.12,0.07,0.01]}'
JSON.SET movie:048 $ '{"title":"Fight Club","genres":["Drama"],"plot":"An insomniac and a soap maker form an underground fight club.","year":1999,"embedding":[0.13,-0.02,0.24,0.07,-0.08,0.10,0.05,-0.01]}'
JSON.SET movie:049 $ '{"title":"The Silence of the Lambs","genres":["Crime","Drama","Thriller"],"plot":"A young FBI cadet seeks help from an imprisoned cannibal.","year":1991,"embedding":[0.11,-0.03,0.22,0.06,-0.09,0.08,0.04,-0.02]}'
JSON.SET movie:050 $ '{"title":"The Departed","genres":["Crime","Drama","Thriller"],"plot":"An undercover cop and a mole in the police attempt to identify each other.","year":2006,"embedding":[0.12,-0.02,0.23,0.07,-0.08,0.09,0.05,-0.01]}'
JSON.SET movie:51 $ '{"title":"Saturday Night Fever","year":1977,"genres":["Drama","Music"],"plot":"A young man finds escape from his mundane life through disco dancing.","embedding":[0.154,-0.050,0.300,0.150,-0.150,0.154,0.034,-0.118]}'
JSON.SET movie:52 $ '{"title":"The Rose","year":1979,"genres":["Music","Drama"],"plot":"A rock star struggles with fame, addiction, and love.","embedding":[0.144,-0.055,0.295,0.145,-0.160,0.150,0.030,-0.120]}'
JSON.SET movie:53 $ '{"title":"Cabaret","year":1972,"genres":["Drama","Music"],"plot":"A performer and a writer navigate love and politics in pre-WWII Berlin.","embedding":[0.151,-0.052,0.297,0.148,-0.152,0.150,0.031,-0.121]}'
JSON.SET movie:54 $ '{"title":"Tommy","year":1975,"genres":["Drama","Music","Fantasy"],"plot":"A deaf and blind boy becomes a pinball champion and religious figure.","embedding":[0.149,-0.051,0.301,0.149,-0.151,0.153,0.033,-0.119]}'
JSON.SET movie:55 $ '{"title":"All That Jazz","year":1979,"genres":["Drama","Music"],"plot":"A choreographer reflects on his life and art while facing death.","embedding":[0.153,-0.049,0.299,0.151,-0.149,0.152,0.032,-0.117]}'
```

### Create a vector-enabled search index
Redis stores movie data as JSON documents with text fields (title, genres, plot) and vector embeddings. Indexing enables fast filtering and approximate nearest neighbor (ANN) searches on embeddings.

```redis:[run_confirmation=true] Create a Vector Index
FT.CREATE idx:movies ON JSON PREFIX 1 "movie:" SCHEMA
  $.title AS title TEXT
  $.genres[*] AS genres TAG
  $.plot AS plot TEXT
  $.year AS year NUMERIC
  $.embedding AS embedding VECTOR FLAT
    6
        TYPE FLOAT32
        DIM 8 // DIM = embedding size, DIM 8 is just for demo purposes. In real use, embeddings are usually 128–1536 dimensions.
        DISTANCE_METRIC COSINE // COSINE = measures semantic closeness
 ```

This sets the stage for combined textual and semantic search.

### Semantic search by query embedding
When users search “I want a fun animated movie about toys and friendship”, sentence-transformers models can convert the text into a vector. You can store and query this vector in Redis for semantic search.

```redis:[run_confirmation=false] Search Per Plot
FT.SEARCH idx:movies "*=>[KNN 3 @embedding $vec AS score]"
  PARAMS 2 vec "\x9a\x99\x19\x3f\xcd\xcc\xcc\x3d\x9a\x99\x4c\x3f\x9a\x99\x33\x3e\x9a\x99\x33\x3f\xcd\xcc\x66\x3e\xcd\xcc\xcc\x3d\xcd\xcc\x4c\x3e"
  SORTBY score
  RETURN 3 title plot score
  DIALECT 2
```

Redis returns top movies with embeddings close to the query vector - Toy Story ranks first, even if keywords don’t exactly match.

### Adding filters for hybrid search

Now, find music movies:
“A feel-good film about music and students.”

You can combine a genre filter with vector similarity:

```redis:[run_confirmation=false] Search Per Genre
FT.SEARCH idx:movies "@genres:{Music} =>[KNN 5 @embedding $vec AS score]"
  PARAMS 2 vec "\x9a\x99\x1d\x3e\xcd\xcc\x4c\xbd\x9a\x99\x99\x3e\x9a\x99\x19\x3e\x9a\x99\x19\xbe\x9a\x99\x1d\x3e\xcd\xcc\x0c\x3e\x9a\x99\xf1\xbc"
  SORTBY score
  RETURN 3 title genres score
  DIALECT 2
```

This hybrid query uses Redis’s tagging system plus vector search, improving relevance.

### Using embeddings of existing movies for recommendations

You love the movie Inception and want to search for similar movies. Retrieve the Inception plot embedding and use it as the query vector:
```redis:[run_confirmation=false] Get the Embedding From the Movie Document
JSON.GET movie:006 $.embedding
```
Now run vector similarity search using that embedding as a binary.

```redis:[run_confirmation=false] Search for Similar Movies
FT.SEARCH idx:movies "*=>[KNN 5 @embedding $vec AS score]" 
    PARAMS 2 vec "\xCD\xCC\x56\x3E\x9A\x99\xF3\xBC\xCD\xCC\x00\x3F\x66\x66\x34\x3E\xC6\xF5\x1B\xBE\x9A\x99\x4D\x3E\x9A\x99\x99\x3D\x9A\x99\xB5\xBD" 
    SORTBY score 
    RETURN 2 title score 
    DIALECT 2
```

Redis finds movies like Arrival and The Departed, showcasing content-based recommendation with semantic similarity.

### Combining metadata filters with vector search
Find classic musical rebellion films from the 90s:
```redis:[run_confirmation=false] Search for Classical Musical Rebellion Films
FT.SEARCH idx:movies "(@genres:{Music} @year:[1970 1979]) =>[KNN 5 @embedding $vec AS score]"
  PARAMS 2 vec "\x9a\x99\x1d\x3e\xcd\xcc\x4c\xbd\x9a\x99\x99\x3e\x9a\x99\x19\x3e\x9a\x99\x19\xbe\x9a\x99\x1d\x3e\xcd\xcc\x0c\x3e\x9a\x99\xf1\xbc"
  SORTBY score
  RETURN 4 title year genres score
  DIALECT 2
```
This shows how Redis vector search works seamlessly with numeric and tag filters.

### Personalizing recommendations
You like Animated and Sci-Fi movies. Personalize results by filtering the vector search:
```redis:[run_confirmation=false] Search Per Genres
FT.SEARCH idx:movies '@genres:{"Animated"|"Sci-Fi"} =>[KNN 5 @embedding $vec AS score]'
   PARAMS 2 vec "\x9a\x99\x1d\x3e\xcd\xcc\x4c\xbd\x9a\x99\x99\x3e\x9a\x99\x19\x3e\x9a\x99\x19\xbe\x9a\x99\x1d\x3e\xcd\xcc\x0c\x3e\x9a\x99\xf1\xbc"
   SORTBY score
   RETURN 3 title genres score
   DIALECT 2
```

This makes Redis recommendations responsive to evolving user preferences without retraining embeddings.

### Next steps
  - Learn more about building personalized recommendations with this [workshop](https://github.com/redis-developer/redis-movies-searcher/tree/springio-2025-workshop).
  - Build a UI for natural language queries that delivers instant semantic recommendations.
  - Add personalization by merging user preferences with semantic search.
  - Explore advanced vector search methods like HNSW indexing for large datasets.
