import select

connection.setblocking(0)

while True:
  status = select.select([connection], [], [], 0.01)
  if status[0]:
    cmd = connection.recv(8)
    
    
    
####

import collections
import _thread
import time

def main():

    def producer(q):
       i = 0
       while True:
           q.append(i)
           i+=1
           time.sleep(0.75)


    def consumer(q):
        while True:
            try:
                v = q.popleft()
                print(v)
            except IndexError:
                print("nothing to pop...queue is empty")
                sleep(1)

    deq = collections.deque(maxlen=1)
    print("starting")
    _thread.start_new_thread(producer, (deq,))
    _thread.start_new_thread(consumer, (deq,))