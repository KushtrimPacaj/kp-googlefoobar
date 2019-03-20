# googlefoobar

Solutions to the first three challenges to googlefoobar.

The first two are quite straighforward. For the third one, a more creative solution was needed. This solution models the cheesbord as an graph with squres as vertexes and edges of length 1 connecting squares from which a knight can jump to. For example (0,0) and (1,3) would be connected. Then using Djikstra's Algorithm, the shortest path from A to B is calculated, and the length is returned as the answer.

Didn't have time to do more ¯\_(ツ)_/¯
