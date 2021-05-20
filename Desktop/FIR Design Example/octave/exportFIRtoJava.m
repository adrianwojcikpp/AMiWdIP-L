## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-05-08

function exportFIRtoJava(b, x_init, ts, decimalPrecision, package, filename)
  pk = ['package ' package ';\n\n'];
  class = 'public final class MyFirData {\n';
  sampletime = ['\tpublic static Double sampletime = ' num2str(ts) ';\n'];
  barray = '\tpublic static Double[] feedforward_coefficients = {\n';
  xarray = '\tpublic static Double[] state = {\n';
 
  for i = 1 : length(b)-1
    barray = [barray '\t\t' double2str(b(i),decimalPrecision) ',\n'];
    xarray = [xarray '\t\t' double2str(x_init(i),decimalPrecision) ',\n'];
  endfor
    barray = [barray '\t\t' double2str(b(end),decimalPrecision) '\n\t};\n'];
    xarray = [xarray '\t\t' double2str(x_init(end),decimalPrecision) '\n\t};\n}'];
  
  filecontent = [pk class sampletime barray xarray];

  fileID = fopen(filename,'w');
  fprintf(fileID,filecontent);
  fclose(fileID);
endfunction
