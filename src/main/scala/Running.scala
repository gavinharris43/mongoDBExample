
  class Running(var open: Boolean) {
    var running = open

    def close: Unit ={
      running = false
    }
    def isOpen:Boolean ={
      running
    }
  }

