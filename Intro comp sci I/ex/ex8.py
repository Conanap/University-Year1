# Albion Fung
# V 0.0.0
# Nov 16, 2015

class LightSwitch():
    '''creates a light switch that can be turned on or off.'''
    def __init__(self, status):
        '''(self, str) -> NoneType
        initializing method
        Given status, this will create a light switch with the specified status
        REQ: status is a string, either 'on' or 'off'. No uppercase. Otherwise
        default to 'off'.
        >>>a = LightSwitch('on')
        print(a)
        I am on
        >>>a = LightSwitch('off')
        print(a)
        I am off
        >>>a = LightSwitch('BLAH')
        print(a)
        I am off
        >>>a = LightSwitch('ON')
        I am off
        '''
        # if status says on
        if(status == 'on'):
            # make status on
            self._status = True
        else:
            # otherwise it's false
            self._status = False

    def __str__(self):
        '''(self) -> str
        Returns a string indicating if the switch is on or off when the
        object is printed.
        >>>a = LightSwitch('on')
        >>>print(a)
        I am on
        >>>a = LightSwitch('off')
        >>>print(a)
        I am off
        '''
        # if it's on
        if(self._status):
            # return it's on
            return "I am on"
        else:
            # otherwise return off
            return "I am off"

    def turn_on(self):
        '''(self) -> NoneType
        Turns the switch off regardless of previous status
        >>>a = LightSwitch('on')
        >>>a.turn_on()
        >>>print(a)
        I am on
        >>>a = LightSwitch('off')
        >>>a.turn_on()
        >>>print(a)
        I am on
        '''
        # turns it on
        self._status = True

    def turn_off(self):
        '''(self) -> NoneType
            Turns the switch off regardless of previous status
            >>>a = LightSwitch('on')
            >>>a.turn_off()
            >>>print(a)
            I am off
            >>>a = LightSwitch('off')
            >>>a.turn_off()
            >>>print(a)
            I am off
            '''
        # turns it off
        self._status = False

    def flip(self):
        '''(self) -> NoneType
        Flips the switch, making it the opposite status.
        >>>a = LightSwitch('on')
        >>>a.flip()
        >>>print(a)
        I am off
        >>>a = LightSwitch('off')
        >>>a.flip()
        >>>print(a)
        I am on
        '''
        # flip it
        self._status = not self._status

    def get_status(self):
        '''(self) -> NoneType
        returns the status of the switch. Not meant for external use
        >>>a = LightSwitch('on')
        >>>a.get_status()
        True
        >>>a.flip()
        >>>a.get_status()
        False
        '''
        # return status
        return self._status


class SwitchBoard():
    '''Creates a switch board with a specific amount of light switches.
    Switches are off by default.'''

    def __init__(self, switch_num):
        '''(self, int) -> NoneType
        Initializing method. Ceates the number of switches specified, and
        makes them off.
        '''
        # initialize a list of switches
        self._switches = []
        # loop until number of switches are created
        for i in range(switch_num):
            # make a new switch that's off
            _new_switch = LightSwitch('off')
            # add the switch to the list
            self._switches.append(_new_switch)

    def __str__(self):
        '''(self) -> NoneType
        Returns a string when the object is being print. It returns
        'The following switches are on: ", followed by the switch numbers
        that are on.
        >>>a = SwitchBoard(2)
        >>>print(a)
        The following switches are on:
        >>>a.flip(1)
        >>>print(a)
        The following switches are on:  1
        '''
        # create initial string we print anyways
        _out = "The following switches are on:"
        # for each switch
        for i in range(len(self._switches)):
            # check if it's on
            if(self._switches[i].get_status()):
                # if it is, add it to the string with a space in front
                status = str(i)
                _out += (" " + status)
        # return the string
        return _out

    def which_switch(self):
        '''(self) -> list of int
        Returns a list of switches that are on.
        >>>a = SwitchBoard(2)
        >>>a.which_switch()
        []
        >>>a.flip(1)
        >>a.which_switch()
        [1]
        '''
        # creates initial list
        _out = []
        # check if each switch is on
        for i in range(len(self._switches)):
            if(self._switches[i].get_status()):
                # if it is, add it to the list
                status = [i]
                _out += status
        # return the list
        return _out

    def flip(self, switch_num):
        '''(self, int) -> NoneType
        flips the switch at the specified index. If it's out of range,
        nonthing wil be done.
        REQ: switch_num < amount of switches
        >>>a = SwitchBoard(2)
        >>>print(a)
        The following switches are on:
        >>>a.flip(1)
        print(a)
        The following witches are on:  1
        '''
        # if the switch exists
        if(switch_num < len(self._switches)):
            # flip it
            self._switches[switch_num].flip()

    def flip_every(self, step):
        '''(self, int) -> Nonetype
        Flips every step amount of switches.
        REQ: step > 0
        >>>a = SwitchBoard(4)
        >>>a.flip_every(2)
        >>>print(a)
        The following switches are on:  0 2 4
        '''
        # flip each switch every certain amount of step
        for i in range(0, len(self._switches), step):
            self.flip(i)

    def reset(self):
        '''(self) -> Nonetype
        Makes all the switches in the switchboard off again
        >>>a = SwichBoard(3)
        >>>a.flip(1)
        a.which_switch()
        [1]
        >>>a.reset()
        >>>a.which_switch()
        []
        '''
        # turn each switch off
        for i in range(len(self._switches)):
            self._switches[i].turn_off()
