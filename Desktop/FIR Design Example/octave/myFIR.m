## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-04-30
function [y, state] = myFIR(x, state, b)

% update state
state = [x, state(1 : end-1)];

% compute output
y = 0;
for i = 1 : length(b)
  y = y + b(i)*state(i);
endfor

endfunction
