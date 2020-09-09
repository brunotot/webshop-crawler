package com.crawler.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LogTracker {

	private Object object;
	
	private Integer current;
	
	private Long startTime;
	
	private Integer totalSize;
	
	private Integer totalSizeUnchanged;
	
	public LogTracker(Object object) throws Exception {
		if (object == null || (!(object instanceof Map) && !(object instanceof List))) {
			throw new Exception("Object instance invalid");
		}
		this.object = object;
		this.startTime = null;
		this.current = 1;
		if (object instanceof List) {
			List<?> list = (List<?>) object;
			totalSize = list.size();
			totalSizeUnchanged = totalSize;
		} else {
			Map<?, ?> map = (Map<?, ?>) object;
			totalSize = map.size();
			totalSizeUnchanged = totalSize;
		}
	}
	
	public String track(int appender) {
		this.totalSize = this.totalSizeUnchanged / appender;
		if (this.startTime == null) {
			this.startTime = System.currentTimeMillis();
			this.current = 1;
		} else {
			this.current++;
		}
		
		return getProgress(this.startTime, this.totalSize, this.current);
	}
	
	public void stop() {
		this.startTime = null;
		this.current = 1;
	}
	
	private String getProgress(long startTime, long total, long current) {
	    long eta = current == 0 ? 0 : 
	        (total - current) * (System.currentTimeMillis() - startTime) / current;

	    String etaHms = current == 0 ? "N/A" : 
	            String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
	                    TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
	                    TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

	    StringBuilder string = new StringBuilder(140);   
	    int percent = (int) (current * 100 / total);
	    string
	        .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
	        .append(String.format(" %d%% [", percent))
	        .append(String.join("", Collections.nCopies(percent, "=")))
	        .append('>')
	        .append(String.join("", Collections.nCopies(100 - percent, " ")))
	        .append(']')
	        .append(String.join("", Collections.nCopies(current == 0 ? (int) (Math.log10(total)) : (int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
	        .append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

	    return new String(string);
	}
	
}
