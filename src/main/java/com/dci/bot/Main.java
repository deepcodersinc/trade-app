package com.dci.bot;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import com.dci.bot.model.Position;
import com.dci.bot.ws.ConnectionStatusListner;
import com.dci.bot.ws.TradeFeed;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocketException;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		PropertyUtil.INSTANCE.setPropertyFile("environment.properties");

		scanner = new Scanner(System.in);
		List<Position> positions = new ArrayList<Position>();
		Position position;
		boolean another = true;

		try {
/*
			while (another) {
				position = new Position();

				System.out.print("Enter productId: ");
				position.setProductId(scanner.next());

				System.out.print("Enter buy price: ");
				float buyPrice = scanner.nextFloat();
				position.setBuyPrice(buyPrice);

				System.out.print("Enter sell price upper limit: ");
				position.setSellPriceUpperLimit(scanner.nextFloat());
				validateInput("sell upper limit must be greater than buy price", position.getSellPriceUpperLimit(),
						p -> p <= buyPrice);

				System.out.print("Enter sell price lower limit: ");
				position.setSellPriceLowerLimit(scanner.nextFloat());
				validateInput("sell lower limit must be less than buy price", position.getSellPriceLowerLimit(),
						p -> p >= buyPrice);

				positions.add(position);

				System.out.print("\nAnother Position? [y | n]: ");
				String anotherTxt = scanner.next();
				another = anotherTxt.compareTo("y") == 0 ? true : false;

			}
*/
			TradeFeed.INSTANCE.createWSConnection(new ConnectionStatusListner());
			
			positions.add(new Position("sb26493", 12800.0F, 12900.0F, 12700.0F));
			positions.add(new Position("sb26502", 1.19F, 1.2F, 1.18F));
			
			for (Position p : positions) {
				TradeFeed.INSTANCE.subscribe(p);
			}

			//TradeFeed.INSTANCE.subscribe(new Position("sb26493", 12800.0F, 12420.0F,
			// 12300.0F));
			// TradeFeed.INSTANCE.subscribe(new Position("sb26502", 1.19F, 1.8F, 1.2F));

		} catch (InputMismatchException ime) {
			System.out.println("Error on input");
		} catch (WebSocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void validateInput(String message, Float val, Predicate<Float> p) {
		if (p.test(val)) {
			System.out.println(message);
			System.out.println("Exiting... try again");
			System.exit(1);
		}
	}
}
