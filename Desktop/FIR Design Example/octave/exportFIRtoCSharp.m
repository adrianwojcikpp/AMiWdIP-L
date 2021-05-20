## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-05-07

function exportFIRtoCSharp(b, x_init, ts, decimalPrecision, namespace, filename)
  
  ns = ['namespace ' namespace '\n{\n'];
  class = '\tpublic static class MyFirData\n\t{\n';
  sampletime = ['\t\tpublic static double sampletime = ' num2str(ts) ';\n'];
  barray = '\t\tpublic static double[] feedforward_coefficients = \n\t\t{\n';
  xarray = '\t\tpublic static double[] state = \n\t\t{\n';
  
  for i = 1 : length(b)-1
    barray = [barray '\t\t\t' num2str(b(i), decimalPrecision) ',\n'];
    xarray = [xarray '\t\t\t' num2str(x_init(i), decimalPrecision) ',\n'];
  endfor
    barray = [barray '\t\t\t' num2str(b(end), decimalPrecision) '\n\t\t};\n'];
    xarray = [xarray '\t\t\t' num2str(x_init(end), decimalPrecision) '\n\t\t};\n\t}\n}'];
  
  filecontent = [ns class sampletime barray xarray];

  fileID = fopen(filename,'w');
  fprintf(fileID,filecontent);
  fclose(fileID);
    
endfunction
