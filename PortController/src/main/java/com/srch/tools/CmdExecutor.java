package com.srch.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.srch.tools.main.ProcessKillVO;



public class CmdExecutor {

	public List<ProcessKillVO> identifyPort() throws IOException {
		String[] netStat = { "netstat", "-ano" };
		Process proc = executeCommands(netStat);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		// Read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		List<ProcessKillVO> procList = new ArrayList<>();
		ProcessKillVO procVO = null;
		while ((s = stdInput.readLine()) != null) {
			if (s.contains("TCP") || s.contains("UDP")) {
				procVO = new ProcessKillVO();
				String[] splitted = s.split("\\s+");
				int value = 0;
				for (String split : splitted) {
					if (!split.isEmpty()) {
						switch (value) {
						case 0:
							procVO.setProtocl(split.trim());
							break;
						case 1:
							String ipaddress = split.trim();
							String[] ipstr = null;

							if (!ipaddress.contains("[")) {
								ipstr = ipaddress.split(":");
							} else {
								ipstr = ipaddress.split("]:");
							}

							if (ipstr.length > 1) {
								if (!ipaddress.contains("[")) {
									procVO.setLocalAddress(ipstr[0].trim());
								} else {
									procVO.setLocalAddress(ipstr[0].trim() + "]");
								}
								procVO.setPort(ipstr[1].trim());
							}
							break;
						case 3:
							if (!split.matches("\\d+")) {
								procVO.setState(split.trim());
							} else {
								procVO.setPId(split.trim());
							}
							break;
						case 4:
							if (procVO.getPId() == null) {
								procVO.setPId(split.trim());
							}
							break;
						}
						value++;
					}
				}
			}
			if (procVO != null) {
				procList.add(procVO);
			}
		}
		while ((s = stdError.readLine()) != null) {
			procVO = new ProcessKillVO();
			procVO.setStdError(s);
			procList.add(procVO);
		}

		return procList;
	}

	public Process executeCommands(String[] commands) throws IOException {
		Runtime rt = Runtime.getRuntime();
		// String[] commands = {"netstat", "-ano"};
		Process proc = rt.exec(commands);
		return proc;
	}

	public String killProcess(String pId) {
		String[] kill = { "taskkill", "/PID", pId, "/F" };
		try {
			String s = null;
			Process proce = executeCommands(kill);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proce.getInputStream()));
			BufferedReader errInout = new BufferedReader(new InputStreamReader(proce.getErrorStream()));

			while ((s = stdInput.readLine()) != null) {
				return s;

			}
			while ((s = errInout.readLine()) != null) {
				return null;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getPid(String port, List<ProcessKillVO> pList) {

		for (ProcessKillVO val : pList) {
			if (val.getPort().equals(port)) {
				return val.getPId();
			}
		}
		return port;

	}


}
