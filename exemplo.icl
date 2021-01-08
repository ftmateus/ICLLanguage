def START:int = def x:int = def y:ref int = new 1 in !y end in x end;;

def fact(n:int):int =
   def x:ref int = new START value:ref int = new START in
   while !x <= n do
       value := !value * !x;
       x := !x + 1
   end; !value
   end;;
   
def printToZero(n: ref int):bool =
   print !n;
   while !n > 0 do
       n := !n - 1;
       print !n
   end;;

def add3Fact(x:int,y:int,z:int):int =
   fact(x) + fact(y) + fact(z);;

  
print(fact(5)); print fact(0);

printToZero(new 3);

print add3Fact(5,0,0);;
