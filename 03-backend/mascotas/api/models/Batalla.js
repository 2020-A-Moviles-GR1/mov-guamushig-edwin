/**
 * Batalla.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    descripcion: {
      type: 'string'
    },
    pokemons: {
      collection: 'pokemon',
      via: 'batalla'
    }
  },
  tableName: 'batalla',
};

