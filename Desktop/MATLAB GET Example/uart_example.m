%{
 **************************************************************************
 * @file    MATLAB GET Example/uart_example.m
 * @author  Adrian Wojcik
 * @version 1.2
 * @date    19 Jun 2020
 * @brief   Simple MATLAB serial port client example
 **************************************************************************
%}

huart = serial('COM7','BaudRate',9600,'Terminator','LF');
fopen(huart);

N = 1000000;

h = figure();
subplot(3,1,1);
    pTemp = plot(nan(N,1),nan(N,1), 'r');
    xlabel('Time [s]');
    ylabel('Temperature [*C]');
    hold on; grid on;
subplot(3,1,2);
    pPres = plot(nan(N,1),nan(N,1), 'b');
    xlabel('Time [s]');
    ylabel('Pressure [hPa]');
    hold on; grid on;
subplot(3,1,3);
    pLight = plot(nan(N,1),nan(N,1), 'k');
    xlabel('Time [s]');
    ylabel('Light intensity [lx]');
    hold on; grid on;
    
k = 1;
t = 0;
ts = 1.0;

while 1
    
    rawData = fgetl(huart);
    
    if ~isempty(rawData)
        data = jsondecode(rawData);

        updateplot(pTemp,  data.temperature, t, k, N);
        updateplot(pPres,  data.pressure,    t, k, N);
        updateplot(pLight, data.light,       t, k, N);

        drawnow;
    end
    
    t = t + ts;
    k = k + 1;
    
    if ~isempty(h.CurrentCharacter)
        break;
    end
end

fclose(huart);
delete(huart);

function updateplot(hplot, x, y, k, N)
    if k > N
        hplot.YData = circshift(hplot.YData, -1); 
        hplot.YData(end) = str2double(x); 
        hplot.XData = circshift(hplot.XData, -1); 
        hplot.XData(end) = y; 
    else
        hplot.YData(k) = str2double(x); 
        hplot.XData(k) = y; 
    end
end
