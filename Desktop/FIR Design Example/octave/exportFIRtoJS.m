## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-04-30

function exportFIRtoJS(b, x_init, ts, decimalPrecision, filename)

  filtervar = 'var MyFirData = {\n';
  sampletime = ['\tsampletime: ' num2str(ts) ',\n'];
  bvar = '\tfeedforward_coefficients: [\n';
  xvar = '\tstate:[\n';

 
  for i = 1 : length(b)-1
    bvar = [bvar '\t\t' num2str(b(i), decimalPrecision) ',\n'];
    xvar = [xvar '\t\t' num2str(x_init(i), decimalPrecision) ',\n'];
  endfor
    bvar = [bvar '\t\t' num2str(b(end), decimalPrecision) '\n\t],\n'];
    xvar = [xvar '\t\t' num2str(x_init(end), decimalPrecision) '\n\t]\n}'];

  filecontent = [filtervar sampletime bvar xvar];

  fileID = fopen(filename,'w');
  fprintf(fileID,filecontent);
  fclose(fileID);

endfunction
