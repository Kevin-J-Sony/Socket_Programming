
# Java implementation of chats

The goal is to facilitate communication between two computers on a local network.

## SimpleChat

SimpleChat achieves this goal by making the peer both a client and a server. The assumption made is that one peer is aware of the address of the other peer. Each peer runs two threads, a child thread with a ServerSocket to listen for connections, and a parent thread waiting for a connection to start writing. When the ServerSocket finally accepts, the child firsts sends information to the parent thread containing the information needed to connect to the other peer's ServerSocket. The child then continues reading information from the peer. Likewise, the parent thread writes information to the peer. In this way, communication occurs.

## BroadcastChat

BroadcastChat achieves communication by having the peers broadcast information. As long as one of the peers gets the information, they are then able to communicate using the same method SimpleChat uses.
