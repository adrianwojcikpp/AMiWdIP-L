classdef MyFir
  properties 
    feedforward_coefficients = 0; %%!< Vector of FIR filter feedforward 
    state = 0;                    %%!< Vector of FIR filter state values
  endproperties
  
  methods
    % @brief Initialization of FIR filter algorithm. 
    % @param ffc : Vector of FIR filter feedforward coefficients.
    % @param st  : Vector of FIR filter input initial state.
    %              Must be the same size as coefficients vector.
    function this = MyFir(fft, st)
      this.feedforward_coefficients = fft;
      this.state = st;
    endfunction
    
    % @brief Execute one step of the FIR filter algorithm.
    % @param x : Input signal.
    % @return Output [filtered] signal.
    function [xf, this] = Execute(this, x)
      % update state
      this.state = [x, this.state(1 : end-1)];
      % compute output
      xf = 0;
      for i = 1 : length(this.state)
        xf = xf + this.feedforward_coefficients(i)*this.state(i);
      endfor
    endfunction
    
    % Display function overwrite
    function disp(this)
      disp("Feedforward coefficients:");
      disp(this.feedforward_coefficients);
      disp("State:");
      disp(this.state);
    endfunction
    
  endmethods
endclassdef