# By Albion Fung
# V0.0.4
# Nov 11, 2015
# Functions for running an encryption or decryption.
# The values of the two jokers.
JOKER1 = 27
JOKER2 = 28
# declare what the alphabets are
# change this if you aren't using english
alphabets = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

# Write your functions here:


def read_deck(deck_file):
    '''(io.TextIOWrapper) -> list
    The function reads the file and returns a list of integers,
    where the integers are in order as it is in the file. The fuction does
    not open or close the file; this must be done before and after the function
    call respectively
    '''
    # read the entire document and strip it
    deck = deck_file.read()
    deck = deck.strip()
    # starting from the first character, get characters smaller than 28
    # put those into a list
    i = 0
    deck_list = []
    # make sure we don't try to access characters that don't exist
    while (i < len(deck)):
        # since strip doesn't strip space and new lines in between characters
        # we make sure to bypass those by checking the next to characters
        if((deck[i] != " " and deck[i] != "\n") and deck[i] != "\t"):
            # if the next 2 characters are not spaces or empty things
            if((deck[i+1] != " " and deck[i+1] != "\n") and deck[i+1] != "\t"):
                # if the next to characters combined does not represent a
                # number bigger than the biggest joker, we add that number
                # to the list
                if(int(deck[i:i+2]) <= JOKER2):
                    next_num = int(deck[i:i + 2])
                    i += 2
                # otherwise, we only add the first character as the number
                else:
                    next_num = int(deck[i:i + 1])
                    i += 1
            # if the second character is a space, we know the current character
            # at index i is the number we want.
            else:
                next_num = int(deck[i:i + 1])
                i += 1
            deck_list.append(next_num)
        # if it's an empty character, we skip it
        else:
            i += 1
    # return the list
    return deck_list


def read_messages(msg_file):
    '''(io.TextIOWrapper) -> list
    Given a file, it reads the text, and organizes each line into a message.
    It then returns the list where each element is 1 message. (Also known as
    1 line per element)
    '''
    # read the first line so the while loop will run
    nextline = msg_file.readline()
    # declare the initial list
    msg_list = []
    # While the next line is not empty
    while(nextline != ''):
        # strip the line and add it to the list
        nextline = nextline.strip()
        msg_list.append(nextline)
        # read the next line
        nextline = msg_file.readline()
    # return the messages lists
    return msg_list


def get_next_keystream_value(deck):
    '''(list of ints) -> int
    Gets the next keystream number that is less the values of the jokers
    '''
    # get next value
    next_var = get_next_value(deck)
    # if it is 27 or 28, get a new one
    while(next_var >= JOKER1):
        next_var = get_next_value(deck)
    # return value
    return next_var


def get_next_value(deck):
    '''(list of ints) -> int
    The function returns the next possible keystream value
    '''
    # move the first joker to the place of its index + 1
    move_joker_1(deck)
    # move the second joker to the place of its index + 2
    move_joker_2(deck)
    # triple cut using jokers as cut off points (google triple cut)
    triple_cut(deck)
    # move the top specified number of cards to the bottom but before
    # the last card
    insert_top_to_bottom(deck)
    # get the value indexed by the first card
    value = get_card_at_top_index(deck)
    # return the value
    return value


def move_joker_1(deck):
    '''(list of int) -> NoneType
    Moves the joker 1 down 1 index (AKA index + 1). Indexes are treated like
    circles (see example)
    REQ: deck be a list of ints with 1 value equal to that of JOKER1
    >>>deck = [1,2,3,4,5]
    >>>JOKER1 = 2
    >>>move_joker_1(deck)
    >>>deck
    [1,3,2,4,5]
    >>>JOKER1 = 5
    >>>move_joker_1(deck)
    >>>deck
    [5,3,2,4,1]
    '''
    # find the index of JOKER1
    jkr_i = deck.index(JOKER1)
    # call the swap function to swap it
    swap_cards(deck, jkr_i)


def move_joker_2(deck):
    '''(list of ints) -> NoneType
    Function moves the second joker (JOKER2) down 2 indexes (AKA index + 2).
    Indexes are treated like circles (see example)
    REQ: deck be a list of ints with 1 value equal to that of JOKER2
    >>>deck = [1,2,3,4,5]
    >>>JOKER2 = 2
    >>>move_joker_2(deck)
    >>>deck
    [1,3,4,2,5]
    >>>JOKER2 = 5
    >>>move_joker_2(deck)
    >>>deck
    [3,5,4,2,1]
    '''
    # swap the items twice
    for i in range(2):
        # get index of JOKER2
        jkr_i = deck.index(JOKER2)
        # swap the number with the one after
        swap_cards(deck, jkr_i)


def swap_cards(deck, index):
    '''(list of ints, int) -> NoneType
    Swaps the int at the index with the int at index + 1 for the list deck.
    Treats index like circles (see example)
    >>>deck = [1,2,3,4]
    >>>swap_cards(deck,1)
    >>>deck
    [1,3,2,4]
    >>>swap_cards(deck,3)
    >>>deck
    [4,3,2,1]
    '''
    # if it's the last card in the deck
    if(index == len(deck) - 1):
        # switch it with the first card in the deck
        (deck[index], deck[0]) = (deck[0], deck[index])
    # otherwise
    else:
        # swap it with the element immediately after it
        (deck[index], deck[index + 1]) = (deck[index + 1], deck[index])


def triple_cut(deck):
    '''(list of ints) -> NoneTpe
    Does a triple cut on the provided deck, using JOKER1 and JOKER2 as cut off
    points. Order of jokers don't matter
    REQ: deck must have JOKER1 and JOKER2 values in it
    >>>deck = [1,2,3,27,4,5,28,6,7]
    >>>triple_cut(deck)
    >>>deck
    [6,7,27,4,5,28,1,2,3]
    '''
    # find index of joker 1 and 2
    jkr_1 = deck.index(JOKER1)
    jkr_2 = deck.index(JOKER2)
    # split the list into 3 parts
    if(jkr_2 > jkr_1):
        (deck[jkr_2 + 1:], deck[:jkr_1]) = (deck[:jkr_1], deck[jkr_2 + 1:])
    else:
        (deck[jkr_1 + 1:], deck[:jkr_2]) = (deck[:jkr_2], deck[jkr_1 + 1:])
    # put it back together in new order


def insert_top_to_bottom(deck):
    '''(list of ints) -> NoneType
    Takes the top card and use it as index. Move the index amount of cards
    to the bottom of the deck, before the last card however. If the number is
    JOKER2, JOKER1 is used instead. (default: JOKER1 = 27, JOKER2 = 28)
    REQ: the list must be of ints.
    >>>deck = [4,3,5,2,1,6,7]
    >>>insert_top_to_bottom(deck)
    >>>deck
    [1,6,4,3,5,2,7]
    '''
    # see top card; if it's 28, use 27
    move = deck[-1]
    if(move == JOKER2):
        move = JOKER1
    # put the top so many cards to bottom
    top_cards = deck[:move]
    del deck[:move]
    bot_card = [deck.pop(-1)]
    deck += top_cards
    deck += bot_card


def get_card_at_top_index(deck):
    '''(list of ints) -> int
    Gets and returns the value using the first element as the index of the
    deck. If the first element is JOKER2 (28), JOKER1 (27) is used instead.
    REQ: deck must be a list of ints
    >>>deck = [1,2,3,4]
    >>>get_card_at_top_index(deck)
    1
    >>>deck = [28,1,2,3,4....9,12] (There are 28 elements)
    >>>get_card_at_top_index(deck)
    9
    '''
    # get top card value
    index = deck[0]
    # change value to JOKER1 if value is JOKER2
    if(index == JOKER2):
        index = JOKER1
    # get the indexed card
    value = deck[index]
    # return the value
    return value


def encrypt_letter(character, keystream):
    '''(str, int) -> str
    Given a letter, the function will encrypt the letter
    by adding the value of the letter (0 = A, 1 = B, 25 = Z, etc) to the
    keystream value. If the resulting value is bigger than the length of all
    alphabets, we subtract the number of alphabets to get the new alphabet.
    The function returns this encrypted letter.
    REQ: character be capital, and only 1 letter. No numbers or punctuation
    >>>encrypt_letter('A',3)
    'D'
    >>>encrypt_letter('Z',1)
    'A'
    '''
    # find index of the character given
    char_num = alphabets.index(character)
    # encrypt by adding keystream value to index
    new_num = char_num + keystream
    # if result is larger than length of alphabets, subtract the length
    # of alphabets
    if(new_num >= len(alphabets)):
        new_num -= len(alphabets)
    # find out what character the new number represents
    new_alphabet = alphabets[new_num]
    # return the new character
    return new_alphabet


def decrypt_letter(character, keystream):
    '''(str, int) -> str
    Given an encrypted letter, the function decrypts the character by
    subtracting the keystream value from the value of the character (0 = A,
    1 = B, 25 = Z, etc). If the resulting value is smaller than 0, the
    function adds the length of all the alphabets to it, and get the new
    alphabet with that value. The function then returns the character.
    REQ: character be an uppercase letter. Only 1 letter. No numbers or
    punctuation
    >>>(decrypt_letter('C',1)
    'B'
    >>>(decrypt_letter('A',2)
    'Y'
    '''
    # get the number of the character given
    char_num = alphabets.index(character)
    # decrypt by subtracting the number by the keystream value
    new_num = char_num - keystream
    # if the resulting value is smaller than 0,
    # add the length of the numbers to it.
    if(new_num < 0):
        new_num = len(alphabets) + new_num
    # find out what character the new value represents
    new_alphabet = alphabets[new_num]
    # return the new character
    return new_alphabet


def clean_message(msg):
    '''(str) -> str
    Given a string, the function removes all numbers, punctuation, space,
    tab, and new line character (\\n). The full list of characters removed:
    1234567890!@#$%^&*()_-=+,./<>?;\'\" (space)\\t\\n
    Then the string is converted the uppercase. The function returns this
    string.
    >>>clean_message('abcd1234')
    'ABCD'
    >>>clean_message('A12,bCd')
    'ABCD'
    '''
    # create an initial string to add to
    clean_msg = ''
    # for each character in the input string,
    # check if it is not one of the characters that don't belong to the
    # alphabets
    for char in msg:
        if(char.isalpha()):
            # if it's part of the alphabet, add it to the clean_msg string
            # after we make it uppercase
            clean_msg += char.upper()
    # return the cleaned string
    return clean_msg


def process_message(deck, msg, mode):
    '''(list of ints, str, str) -> str
    Given the deck of cards, the function returns the ecrypted or decrypted
    message msg, based on the mode. Where 'e' means encrypt, and 'd' means
    decrypt. If you want to decrypt or encrypt a list of messages, use the
    function process_messages(list of int, list of str, str).
    REQ: deck must be a list of ints with 1 to 28 in it
    REQ: mode must be lowercase and either 'd' or 'e'.
    >>>deck = [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21,
       24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26]
    >>>msg = 'This is it! The master sword!'
    >>>mode = 'e'
    >>>process_messages(deck, msg, mode)
    'EQFZSRTEAPNXLSRJAMNGAT'
    >>>deck = [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21,
       24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26]
    >>>msg = 'EQFZSRTEAPNXLSRJAMNGAT'
    >>>mode = 'd'
    >>>process_messages(deck, msg, mode)
    'THISISITTHEMASTERSWORD'
    '''
    # create an initial str
    new_msg = ''
    # clean the msgs
    msg = clean_message(msg)
    # get the length of the msg
    length = number_in_msg(msg)
    # get all the keystream values req'd
    keystreams = get_all_keystream(deck, length)
    # decrypt or encrypt respectively
    # even though if we put the if statements inside the loop we can
    # save 1 line, this saves on operations, since now we only need
    # to check mode once, instead of once every loop
    if(mode == 'd'):
        # decrypt each letter in loops
        for char in range(len(msg)):
            # adds decrypted char into string
            new_msg += decrypt_letter(msg[char], keystreams[char])
    elif(mode == 'e'):
        # encrypt each letter in loop
        for char in range(len(msg)):
            # adds encrypted char to string
            new_msg += encrypt_letter(msg[char], keystreams[char])
    # return encrypted or decrypted msg
    return new_msg


def process_messages(deck, msg, mode):
    '''(list of int, list of str, str) -> list of str
    Given the deck of cards, the function returns the ecrypted or decrypted
    list of messages msg, based on the mode. Where 'e' means encrypt, 'd' means
    decrypt. If you want to decrypt or encrypt a single string, use the
    function process_message(list of int, list of str, str).
    REQ: deck must be a list of ints with values 1-28 in it
    REQ: msg be a list of strings
    REQ: mode must be a sigle letter in lowercase, either 'e' or 'd'
    >>>deck = [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21,
       24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26]
    >>>msg = ['This is it! The master sword!', 'no, this can't be it. Too bad']
    >>>mode = 'e'
    >>>process_messages(deck, msg, mode)
    ['EQFZSRTEAPNXLSRJAMNGAT', 'GLCEGMOTMTRWKHAMGNME']
    >>>deck = [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21,
       24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26]
    >>>msg = ['EQFZSRTEAPNXLSRJAMNGAT', 'GLCEGMOTMTRWKHAMGNME']
    >>>mode = 'd'
    >>>process_messages(deck, msg, mode)
    ['THISISITTHEMASTERSWORD', 'NOTHISCANTBEITTOOBAD']
    '''
    # create a new message list so we don't mess up the original one
    new_msg = msg[:]
    # set counter for keystream value
    counter = 0
    # find total length of the message
    length = number_in_msgs(new_msg)
    # get all keystream values
    keystreams = get_all_keystream(deck, length)
    # for each message
    for i in range(len(msg)):
        # clean the message
        new_msg[i] = clean_message(new_msg[i])
        # create an initial string
        cryptd = ''
        # reduce amount of checks by having if statement outside of loop
        # but it's inside first loop since that would require 5 more lines of
        # code. 1 chck / message isn't too bad
        if(mode == 'e'):
            # if encrypt, loop through the msg and encrypt each char
            for char in range(len(new_msg[i])):
                cryptd += encrypt_letter(new_msg[i][char], keystreams[counter])
                # keeping track of keystream values used
                counter += 1
        elif(mode == 'd'):
            # if decrypt, loop through the msg and decrypt each char
            for char in range(len(new_msg[i])):
                cryptd += decrypt_letter(new_msg[i][char], keystreams[counter])
                # keeping track of keystream values used
                counter += 1
        # make sure to have the crypted msg assigned to the retun list
        new_msg[i] = cryptd
    # return crypted list of strings
    return new_msg


def number_in_msgs(msg):
    '''(list of str) -> int
    Given a list of strings, give you the total of the length of the strings.
    Use number_in_msg() for a single string.
    REQ: msg must be a list containing only strings
    >>>a = ['abc','def']
    >>>number_in_msgs(a)
    6
    '''
    # declare an initial length (0)
    length = 0
    # for each string in the list
    for message in msg:
        # add the length of each string to the total length
        length += len(message)
    # return the length
    return length


def number_in_msg(msg):
    '''(str) -> int
    Given a string, the function returns the length of the string. Used for
    cleaner and more uniform code. Use number_in_msgs() for list of strings.
    >>>a = 'abc'
    >>>number_in_msg(a)
    3
    '''
    # get the length
    length = len(msg)
    # return length
    return length


def get_all_keystream(deck, length):
    '''(list of int, int) -> list of int
    Given a deck and the length of the messages (or keystream values required),
    the function returns a list of keystream values, where the list has length
    many elements (keystream values).
    REQ: deck be a list of int with 1-28 in it
    REQ: length >= 0
    >>>deck = [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21,
       24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26]
    >>> length = 2
    >>>get_all_keystream(deck, length)
    [11,9]
    '''
    # declare an initial list
    keystreams = []
    # loop until we have enough keystream values
    for i in range(length):
        # get the next keystream
        key_stream = get_next_keystream_value(deck)
        # add to the list
        keystreams += [key_stream]
    # return the list
    return keystreams
