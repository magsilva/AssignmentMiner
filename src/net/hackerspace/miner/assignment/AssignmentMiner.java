package net.hackerspace.miner.assignment;


import java.util.HashSet;
import java.util.Set;

import org.kohsuke.github.AbuseLimitHandler;
import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedSearchIterable;
import org.kohsuke.github.RateLimitHandler;

public class AssignmentMiner {
  public static void main(String[] args) throws Exception {
	  // Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024); // 10MB cache
	  GitHub gh = GitHubBuilder.fromEnvironment()
			  .withOAuthToken("679c23e4b672ae76b9788de0fd890762241d7dae") 
	       //.withConnector(new OkHttpConnector(new OkUrlFactor(new OkHttpClient().setCache(cache))))
	      .withRateLimitHandler(RateLimitHandler.WAIT) 
		  .withAbuseLimitHandler(AbuseLimitHandler.WAIT)
	      .build();
	  
	  GHRateLimit limit = gh.lastRateLimit();
	  System.out.println("Current limit: " + limit.remaining);
	  
	  String[] terms = new String[]{
			  "6.00.1x",
			  "6.00",
			  "MIT600x",
			  "MIT_6001",
			  "MITx_600x",
			  "MITx-600.1x"
	  };
	  Set<String> results = new HashSet<String>();
	  
	  for (String term : terms) {
		  GHRepositorySearchBuilder searchBuilder = gh.searchRepositories()
				  .q(term);
		  PagedSearchIterable<GHRepository> repositories = searchBuilder.list();
		  System.out.println(repositories.getTotalCount());
		  for (GHRepository repo : repositories) {
			  String gitUrl = repo.gitHttpTransportUrl();
			  String line = repo.getFullName() + ",  " + gitUrl; 
			  results.add(line);
			  System.out.println(line);
		  }
	  }
	  
	  System.out.println("---------------------------------------------------");
	  for (String result : results) {
		  System.out.println(result);
	  }
  }
}