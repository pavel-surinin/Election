var ProductListComponent = React.createClass({
  render: function() {
    var array = [];
    this.props.products.map(function(product,index) {
      array.push(
        <ProductComponent
          id={product.id}
          key={index}
          title={product.title}
          author={product.author}
          publishedAt={product.publishedAt}
        />
      );
    });
    return (
      <ul class="list-group">
        {array}
      </ul>
    );
  }
});

window.ProductListComponent = ProductListComponent;
