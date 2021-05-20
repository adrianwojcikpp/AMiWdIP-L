function str = double2str(d,p)
  
  str = num2str(d,p);
  if isempty(strfind(str,'.'))
    str = [str '.0'];
  endif
  
endfunction
