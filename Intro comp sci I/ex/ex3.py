# By Albion Fung
# Oct 5, 2015


def percent_to_gpv(percent_mark):
    '''(float)-> float
    given the percent mark, the function returns the person's gpv in a float.
    REQ: 0<= percent_mark <=100
    >>>percent_to_gpv(0)
    0.0
    >>>percent_to_gpv(49)
    0.0
    >>>percent_to_gpv(52)
    0.7
    >>>percent_to_gpv(0)
    56.0
    ...
    '''
    # the code checks if the percent mark of the student is within range of
    # each gpv. It then returns the gpv of the student that matches the range
    if(percent_mark >= 0 and percent_mark <= 49):
        return 0.0
    elif(percent_mark >= 50 and percent_mark <= 52):
        return 0.7
    elif(percent_mark >= 53 and percent_mark <= 56):
        return 1.0
    elif(percent_mark >= 57 and percent_mark <= 59):
        return 1.3
    elif(percent_mark >= 60 and percent_mark <= 62):
        return 1.7
    elif(percent_mark >= 63 and percent_mark <= 66):
        return 2.0
    elif(percent_mark >= 67 and percent_mark <= 69):
        return 2.3
    elif(percent_mark >= 70 and percent_mark <= 72):
        return 2.7
    elif(percent_mark >= 73 and percent_mark <= 76):
        return 3.0
    elif(percent_mark >= 77 and percent_mark <= 79):
        return 3.3
    elif(percent_mark >= 80 and percent_mark <= 84):
        return 3.7
    elif(percent_mark >= 85):
        return 4.0


def card_namer(card_num, suit):
    '''(str,str)->str
    Given a card number, and the first initial of the suit in capital, the
    function returns the name of the card. When a suit or a number that doesn't
    exist in the standard deck of cards is inputted, the function returns
    'CHEATER!'
    REQ: card_num, suit are str; suit, Ace, 10, Jack, Queen and King must be
    Capitalized single digit.
    >>>card_namer("A","D")
    'Ace of Diamonds'
    >>>card_name("T","C")
    'Ten of Clubs'
    >>>("3","Y")
    'CHEATER!'
    '''
    # set default condition of cheating to false; to avoid a syntax
    # error which will say that cheat_num and cheat_suit has not been declared
    # if the user did not cheat
    cheat_num = False
    cheat_suit = False
    # check for which suit
    if(suit == "D"):
        suit_name = "Diamonds"
    elif(suit == "C"):
        suit_name = "Clubs"
    elif(suit == "H"):
        suit_name = "Hearts"
    elif(suit == "S"):
        suit_name = "Spades"
    else:
        cheat_suit = True
    # check for which card number
    if(card_num == "1"):
        number = "Ace"
    elif(card_num == "2"):
        number = "2"
    elif(card_num == "3"):
        number = "3"
    elif(card_num == "4"):
        number = "4"
    elif(card_num == "5"):
        number = "5"
    elif(card_num == "6"):
        number = "6"
    elif(card_num == "7"):
        number = "7"
    elif(card_num == "8"):
        number = "8"
    elif(card_num == "9"):
        number = "9"
    elif(card_num == "T"):
        number = "10"
    elif(card_num == "A"):
        number = "Ace"
    elif(card_num == "J"):
        number = "Jack"
    elif(card_num == "Q"):
        number = "Queen"
    elif(card_num == "K"):
        number = "King"
    else:
        cheat_num = True
    # if the user is not cheating, returns the card. If the usre is cheating,
    # returns 'CHEATER!'
    if(cheat_num or cheat_suit):
        return "CHEATER!"
    else:
        return (number+" of "+suit_name)


def my_str(obj):
    '''(obj)->str
    Given an object, the function recognizes what type of object it is. For
    strings, it returns the object as is; for boolean, it returns 'YES' for
    true and 'NO' for false; for integer, it returns 'Small Number' if obj
    <=10, 'Medium Number' if 10<obj<=99 and 'Large Number' for obj>99; for
    floats, the function rounds it to 2 digits and returns it as a string;
    other object types return 'I dunno'.
    REQ: obj must exist. obj may be None
    >>>my_str("Hi")
    'Hi'
    >>>my_str(True)
    'YES'
    >>>my_str(12)
    'Medium Number'
    >>>my_str(12.1234)
    '12.12'
    >>>my_str(None)
    'I dunno'
    '''
    # check if the obj is specific types and act accordingly.
    if(type(obj) == str):
        return obj
    elif(type(obj) == bool):
        if(obj):
            return "YES"
        else:
            return "NO"
    elif(type(obj) == int):
        if(obj <= 10):
            return "Small Number"
        elif(obj >= 11 and obj <= 99):
            return "Medium Number"
        else:
            return "Large Number"
    elif(type(obj) == float):
        return str(round(obj, 2))
    else:
        return "I dunno"
