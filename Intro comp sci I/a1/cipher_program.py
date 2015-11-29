# By Albion Fung
# V0.0.2
# Nov 11, 2015
#
"""
Encrypt or decrypt the contents of a message file using a deck of cards.
"""

import cipher_functions

DECK_FILENAME = 'deck1.txt'
MSG_FILENAME = 'secret1.txt'
MODE = 'd'  # 'e' for encryption, 'd' for decryption.


def main():
    """ () -> NoneType
    Perform the encryption using the deck from a file called DECK_FILENAME and
    the messages from a file called MSG_FILENAME. If MODE is 'e', encrypt;
    otherwise, decrypt.
    """
    # open the deck file and read it. Get it into a list
    deck_file = open(DECK_FILENAME, "r")
    deck = cipher_functions.read_deck(deck_file)
    deck_file.close()
    # open message file and read it. Get each line into an element of
    # a list
    msg_file = open(MSG_FILENAME, "r")
    msg = cipher_functions.read_messages(msg_file)
    msg_file.close()
    new_msg = cipher_functions.process_messages(deck, msg, MODE)
    # print out result
    for completed_msg in new_msg:
        print(completed_msg)


main()
