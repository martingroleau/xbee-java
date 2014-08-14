/**
* Copyright (c) 2014 Digi International Inc.,
* All rights not expressly granted are reserved.
*
* This Source Code Form is subject to the terms of the Mozilla Public
* License, v. 2.0. If a copy of the MPL was not distributed with this file,
* You can obtain one at http://mozilla.org/MPL/2.0/.
*
* Digi International Inc. 11001 Bren Road East, Minnetonka, MN 55343
* =======================================================================
*/
package com.digi.xbee.api.packet.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digi.xbee.api.models.ATCommandStatus;
import com.digi.xbee.api.models.ATStringCommands;
import com.digi.xbee.api.models.XBee16BitAddress;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.packet.APIFrameType;
import com.digi.xbee.api.packet.XBeeAPIPacket;
import com.digi.xbee.api.utils.ByteUtils;
import com.digi.xbee.api.utils.HexUtils;

/**
 * This class represents a Remote AT Command Response packet. Packet is built using the parameters of 
 * the constructor.
 * 
 * <p>If a module receives a remote command response RF data frame in response to a Remote AT Command
 * Request, the module will send a Remote AT Command Response message out the UART. Some commands may
 * send back multiple frames--for example, Node Discover (ND) command.</p>
 * 
 * <p>This packet is received in response of a {@code RemoteATCommandPacket}.</p>
 * 
 * <p>Response also includes an {@code ATComandStatus} object with the status of the AT command.</p>
 * 
 * @see RemoteATCommandPacket
 * @see ATComandStatus 
 */
public class RemoteATCommandResponsePacket extends XBeeAPIPacket {
	
	// Variables
	private final XBee64BitAddress sourceAddress64;
	
	private final XBee16BitAddress sourceAddress16;
	
	private final ATCommandStatus status;
	
	private final String command;
	
	private byte[] commandValue;
	
	private Logger logger;
	
	/**
	 * Class constructor. Instances a new object of type RemoteCommandResponse with
	 * the given parameters.
	 * 
	 * @param frameID frame ID
	 * @param sourceAddress64 64-bit address of the remote radio returning response
	 * @param sourceAddress16 16-bit network address of the remote
	 * @param command The AT command
	 * @param status The command status.
	 * @param commandValue The AT command response value.
	 * 
	 * @throws NullPointerException if {@code destAddress64 == null} or
	 *                              if {@code destAddress16 == null} or
	 *                              if {@code command == null} or
	 *                              if {@code status == null}.
	 * @throws IllegalArgumentException if {@code frameID < 0} or
	 *                                  if {@code frameID > 255}.
	 * 
	 * @see XBee64BitAddress
	 * @see XBee16BitAddress
	 * @see ATCommandStatus
	 */
	public RemoteATCommandResponsePacket(int frameID, XBee64BitAddress sourceAddress64, XBee16BitAddress sourceAddress16, String command, ATCommandStatus status, byte[] commandValue) {
		super(APIFrameType.REMOTE_AT_COMMAND_RESPONSE);
		
		if (sourceAddress64 == null)
			throw new NullPointerException("64-bit destination address cannot be null.");
		if (sourceAddress16 == null)
			throw new NullPointerException("16-bit destination address cannot be null.");
		if (command == null)
			throw new NullPointerException("AT command cannot be null.");
		if (status == null)
			throw new NullPointerException("AT command status cannot be null.");
		if (frameID < 0 || frameID > 255)
			throw new IllegalArgumentException("Frame ID must be between 0 and 255.");
		
		this.frameID = frameID;
		this.sourceAddress64 = sourceAddress64;
		this.sourceAddress16 = sourceAddress16;
		this.command = command;
		this.status = status;
		this.commandValue = commandValue;
		this.logger = LoggerFactory.getLogger(RemoteATCommandResponsePacket.class);
	}
	
	@Override
	public byte[] getAPIData() {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		data.write(frameID);
		try {
			data.write(sourceAddress64.getValue());
			data.write(sourceAddress16.getValue());
			data.write(ByteUtils.stringToByteArray(command));
			data.write(status.getId());
			if (commandValue != null)
				data.write(commandValue);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return data.toByteArray();
	}
	
	@Override
	public boolean needsAPIFrameID() {
		return true;
	}
	
	/**
	 * Retrieves the 64 bit source address. 
	 * 
	 * @return The 64 bit source address.
	 * 
	 * @see XBee64BitAddress
	 */
	public XBee64BitAddress get64bitSourceAddress() {
		return sourceAddress64;
	}
	
	/**
	 * Retrieves the 16 bit source address.
	 * 
	 * @return The 16 bit source address.
	 * 
	 * @see XBee16BitAddress
	 */
	public XBee16BitAddress get16bitSourceAddress() {
		return sourceAddress16;
	}
	
	/**
	 * Retrieves the AT command response status.
	 * 
	 * @return The AT command response status.
	 * 
	 * @see ATCommandStatus
	 */
	public ATCommandStatus getStatus() {
		return status;
	}
	
	/**
	 * Retrieves the AT command.
	 * 
	 * @return The AT command.
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Sets the AT command value as String.
	 * 
	 * @param commandValue The AT command value as String.
	 */
	public void setCommandValue(String commandValue) {
		if (commandValue == null)
			this.commandValue = null;
		else
			this.commandValue = commandValue.getBytes();
	}
	
	/**
	 * Sets the AT response response value.
	 * 
	 * @param commandValue The AT command response value.
	 */
	public void setCommandValue(byte[] commandValue) {
		this.commandValue = commandValue;
	}
	
	/**
	 * Retrieves the AT command response value.
	 * 
	 * @return The AT command response value.
	 */
	public byte[] getCommandValue() {
		return commandValue;
	}
	
	/**
	 * Retrieves the AT command value as String.
	 * 
	 * @return The AT command value as String, null if no parameter is set.
	 */
	public String getCommandValueAsString() {
		if (commandValue == null)
			return null;
		return new String(commandValue);
	}
	
	@Override
	public LinkedHashMap<String, String> getAPIPacketParameters() {
		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("Frame ID", HexUtils.prettyHexString(HexUtils.integerToHexString(frameID, 1)) + " (" + frameID + ")");
		parameters.put("64-bit source address", HexUtils.prettyHexString(sourceAddress64.toString()));
		parameters.put("16-bit source address", HexUtils.prettyHexString(sourceAddress16.toString()));
		parameters.put("AT Command", HexUtils.prettyHexString(HexUtils.byteArrayToHexString(command.getBytes())) + " (" + command + ")");
		parameters.put("Status", HexUtils.prettyHexString(HexUtils.integerToHexString(status.getId(), 1)) + " (" + status.getDescription() + ")");
		if (commandValue != null) {
			if (ATStringCommands.get(command) != null)
				parameters.put("Response", HexUtils.prettyHexString(HexUtils.byteArrayToHexString(commandValue)) + " (" + new String(commandValue) + ")");
			else
				parameters.put("Response", HexUtils.prettyHexString(HexUtils.byteArrayToHexString(commandValue)));
		}
		return parameters;
	}
}
