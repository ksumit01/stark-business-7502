package com.masai.dao;

import java.util.List;

import com.masai.Utility.EMUtils;

import com.masai.entity.Recipe;

import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomeThingWentWrongException;

import jakarta.persistence.EntityManager;

import jakarta.persistence.Query;

public class RecipeDaoImp implements RecipeDao {

	@Override
	public List<Recipe> viewAllRecipe() throws SomeThingWentWrongException, NoRecordFoundException {

		EntityManager em = null;
		List<Recipe> recipeList = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("FROM Recipe r where r.isDeleted=0");
			recipeList = (List<Recipe>) query.getResultList();
			if (recipeList.size() == 0) {
				throw new NoRecordFoundException("No Recipe Found");
			}
		} catch (IllegalArgumentException ex) {
			throw new SomeThingWentWrongException("Unable to process request, try again later");
		} finally {
			em.close();
		}
		return recipeList;
	}

	@Override
	public List<Recipe> viewRecipesWithGivenIngredients(String ingredients) throws SomeThingWentWrongException, NoRecordFoundException {
	    EntityManager em = null;
	    List<Recipe> recipeList = null;
	    try {
	        em = EMUtils.getEntityManager();
	        Query query = em.createQuery("FROM Recipe r WHERE r.ingredients LIKE :ingredient AND r.isDeleted = 0");
	        query.setParameter("ingredient", "%" + ingredients + "%");  // Use the actual parameter

	        recipeList = (List<Recipe>) query.getResultList();
	        if (recipeList.size() == 0) {
	            throw new NoRecordFoundException("No Recipe Found");
	        }
	    } catch (IllegalArgumentException ex) {
	        throw new SomeThingWentWrongException("Unable to process request, try again later");
	    } finally {
	        em.close();
	    }
	    return recipeList;
	}

	

}