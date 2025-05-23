
# Java implementation of chats

The goal is to facilitate communication between two computers on a local network.

## SimpleChat

SimpleChat achieves this goal by making the peer both a client and a server. The assumption made is that one peer is aware of the address of the other peer. This is not an asynchronous server, meaning that a peer can only write once it reads the contents of the socket. 

## BroadcastChat

BroadcastChat achieves communication by having the peers broadcast information. As long as one of the peers gets the information, they are then able to communicate using the same method SimpleChat uses. This is not an asynchronous server, meaning that a peer can only write once it reads the contents of the socket.


## AsyncChat
