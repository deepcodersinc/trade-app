package com.dci.bot;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import com.dci.bot.model.Position;
import com.dci.bot.ws.FeedManager;
import com.dci.bot.ws.WSConnector;
import com.dci.util.PropertyUtil;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		new PropertyUtil().loadProperties("environment-prod.properties");

		scanner = new Scanner(System.in);
		List<Position> positions = new ArrayList<Position>();
		Position position;

		try {
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

			FeedManager fm = new FeedManager(new WSConnector().getConnection());
			fm.subscribe(position);
			
			//fm.subscribe(new Position("sb26513", 150, 160, 130));

		} catch (InputMismatchException ime) {
			System.out.println("Error on input");
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
