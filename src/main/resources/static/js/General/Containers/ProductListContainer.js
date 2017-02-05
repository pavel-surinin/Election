var ProductListContainer = React.createClass({
  getInitialState: function() {
    return {
      products : []
    };
  },
  componentWillMount: function() {
    var self = this;
    axios.get('/api/books')
    .then(function(response){
      self.setState({products:response.data});
    }).catch(function(err){
      console.log(err);
    });
  },
  render: function() {
    return (
      <ProductListComponent products={this.state.products}/>
    );
  }
});

window.ProductListContainer = ProductListContainer;
