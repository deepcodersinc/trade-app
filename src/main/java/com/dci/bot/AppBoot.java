/*package com.dci.bot;

import java.util.function.Predicate;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.dci.bot.model.Position;
import com.dci.bot.ws.ConnectionStatusListner;
import com.dci.bot.ws.TradeFeed;
import com.dci.util.PropertyUtil;

public class AppBoot {

	public static void main(String[] args) {
		
		Options options = new Options();
		options.addOption("productId", true, "Id of the product to open a position on");
		options.addOption("buyPrice", true, "The price at which the position will be open");
        options.addOption("uSellPrice", true, "The price you are willing to close a position and make a profit. Should be more than buy price.");
        options.addOption("lSellPrice", true, "The price you want are willing to close a position at and make a loss. Should be less than buy price.");
				
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		
		try {
			cmd = parser.parse(options, args);
			
			
			
			
			//validateInput(args1[0], p -> p.length()	< 0);
			//validateInput("Number of arguments must be 4", p -> (p.length() != 3));
			
			
			if (args.length < 4) {
				new HelpFormatter().printHelp("Trading App", options, true);
				System.exit(1);
			}
			PropertyUtil.INSTANCE.setPropertyFile("environment.properties");
		
			Position position = new Position(
									cmd.getOptionValue("productId"),
									Float.parseFloat(cmd.getOptionValue("buyPrice")),
									Float.parseFloat(cmd.getOptionValue("upperSellPrice")),
									Float.parseFloat(cmd.getOptionValue("lowerSellPrice")));
			
			//Inject dependencies and create WebSocket connection
			TradeFeed.INSTANCE.createWSConnection(new ConnectionStatusListner());
			TradeFeed.INSTANCE.subscribe(position);

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
}
*/