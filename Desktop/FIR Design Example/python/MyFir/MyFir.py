
class MyFir:

  # @brief Initialization of FIR filter algorithm. 
  # @param ffc : List of FIR filter feedforward coefficients.
  # @param st  : List of FIR filter input initial state.
  #              Must be the same size as coefficients list.
  def __init__(self, fft, st):
    self.feedforward_coefficients = fft; ##!< List of FIR filter feedforward coefficients
    self.state = st;                     ##!< List of FIR filter state values
  
  # @brief Execute one step of the FIR filter algorithm.
  # @param x : Input signal.
  # @return Output [filtered] signal.
  def Execute(self, x):
    # update state
    self.state.pop()
    self.state.insert(0, x)
    # compute output 
    xf = 0.0
    for i in range(len(self.state)):
      xf += self.feedforward_coefficients[i]*self.state[i]
    return xf
