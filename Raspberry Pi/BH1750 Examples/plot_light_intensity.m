%% Matrix multiplication unit test

%%% File info 
%
% ************************************************************************
%
%  @file     plot_light_intensity.m
%  @author   Adrian Wojcik
%  @version  1.0
%  @date     09-Apr-2021 08:55:55
%  @brief    Simple MATLAB RESTful client
%
% ************************************************************************
%

light_sensor_url = 'http://192.168.1.189/bh1750.php';
t = 0;
ts = 0.1; % [s]

p = plot(nan,nan);
p.LineWidth = 3;
hold on; grid on;
xlabel('Time [s]');
ylabel('Light intensity [lux]');

while 1
    light_measurement = webread(light_sensor_url);
    if length(p.YData) > 100
    p.YData = circshift(p.YData, -1); 
    p.YData(end) = light_measurement.data; 
    p.XData = circshift(p.XData, -1); 
    p.XData(end) = t; 
    else
    p.YData = [p.YData light_measurement.data];
    p.XData = [p.XData t];
    end
    t = t + ts;
    drawnow;
    pause(ts);
end