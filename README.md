## Google FooBar challenges

This repository contains my solutions to the first three challenges to googlefoobar challenge.

#### Technical descriptions
The first two are quite straighforward.

For the third one, a more creative solution was needed.  
This solution models the cheesbord as a graph with squares as vertexes and edges of length 1 connecting squares from which a knight can jump to. For example <i>(0,0)</i> and <i>(1,3)</i> would be connected.  
Then using <b>Djikstra's Algorithm</b>, the shortest path from A to B is calculated, and the length is returned as the answer.

Didn't have time to do more ¯\\\_(ツ)\_/¯
