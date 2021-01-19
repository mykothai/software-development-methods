import React, { useEffect, useState } from 'react';
import Recipe from './Recipe';
import './App.css';

const App = () => {
    // API: https://developer.edamam.com/edamam-docs-recipe-api
    
    const [recipes, setRecipes] = useState([]);
    const [search, setSearch] = useState('');
    const [query, setQuery] = useState('');

    const req = `https://api.edamam.com/search?q=${query}&app_id=${process.env.REACT_APP_APP_ID}&app_key=${process.env.REACT_APP_APP_KEY}`

    useEffect(() => {
        console.log('Get recipes');
        getRecipes()
    }, [query]) // run only when search button clicked

    const getRecipes = async () => {
        const res = await fetch(req); // use await when data isn't expected to come back instantly
        const data = await res.json();
        setRecipes(data.hits);
        console.log(data.hits);
    };

    const updateSearch = event => {
        setSearch(event.target.value);
        console.log(search);
    };

    const getSearch = event => {
        event.preventDefault(); // stop page refresh while search input entered
        setQuery(search);
        console.log('Get search "' + search + '"');
        setSearch(''); // clear search field
    };

    return(
        <div className='App'>
            <h1>Recipes</h1>
            <form onSubmit={getSearch} className="search-form">
                <input className="search-bar" type="text" value={search} placeholder="Search for a recipe" onChange={updateSearch}/>
                <button className="search-button" type="submit">
                    Search
                </button>
            </form>
            <div className="recipes">
                {recipes.map(recipe => (
                    <Recipe
                    key={recipe.recipe.label} // should be unique
                    title={recipe.recipe.label} 
                    calories={recipe.recipe.calories}
                    image={recipe.recipe.image}
                    ingredients={recipe.recipe.ingredients}
                    dietLabel={recipe.recipe.dietLabels}
                    healthLabel={recipe.recipe.healthLabels}
                    time={recipe.recipe.totalTime}
                    servings={recipe.recipe.yield}
                    url={recipe.recipe.url}
                    />
                ))}
            </div>
        </div>
    );
};

export default App;
