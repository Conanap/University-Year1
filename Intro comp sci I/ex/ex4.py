# By Albion Ka Hei Fung
# V0.0.1
# Oct 19, 2015


def insert(listA, listB, index):
        '''(list, list, int) -> list
        or
        (str, str, int) -> str
        Inserts the second list or string into the first list
        or string at the desired index (or location, counting
        from the left, starting at character or element 0
        REQ: listA, listB is a list or string
        REQ: index >= 0
        >>>insert([1,2,3],['a','b','c'],2)
        [1,2,'a','b','c',3]
        >>>insert("123","abc",2)
        '12abc3'
        '''
        # separate the front end and back end of the list
        # and put in the desired elements
        newString = listA[:index] + listB + listA[index:]
        return newString


def up_to_first(given, obj):
        '''(list, object) -> list
        or
        (str, str) -> str
        Given a list or string and an object or second string,
        the function returns a string or list where the object
        first occurs, but not including the object.
        REQ: given is a string or list
        REQ: object must be the same type as the elements within the list
        REQ: object must be str if given is str
        >>>up_to_first([1,2,3,4],3)
        [1,2]
        >>>up_to_first([1,2,3,4],9)
        [1,2,3,4]
        '''
        # see what type the inputs are
        if(type(given) == str):
                # split the string at desired element
                newThing = given.split(obj)
                return newThing[0]
        else:
                # make a loop such that we know which character is the
                # desired one
                # returning will break the loop automatically
                i = 0
                for element in given:
                        if(obj == element):
                                return given[:i]
                        else:
                                i += 1
                return(given[:])


def cut_list(given, index):
        '''(str,int) -> str
        or
        (list,int) -> list
        Given a list or string and an index, it puts the elements
        or characters before the indexed character behind the indexed
        character, and the characters after the indexed characcter
        before the indexed character
        REQ: given is a list or string
        REQ index >=0 and index <= len(given)
        >>>cut_list([0,1,2,3,4,5,6,7,8,9],3)
        [4,5,6,7,8,9,3,0,1,2]
        >>>cut_list("ABCDEFGX1234",7)
        '1234XABCDEFG'
        '''
        return given[index+1:] + given[index:index+1] + given[:index]
